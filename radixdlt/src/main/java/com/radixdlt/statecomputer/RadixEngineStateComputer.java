/*
 * (C) Copyright 2020 Radix DLT Ltd
 *
 * Radix DLT Ltd licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.  See the License for the specific
 * language governing permissions and limitations under the License.
 */

package com.radixdlt.statecomputer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.radixdlt.consensus.Command;
import com.radixdlt.consensus.VerifiedCommittedHeader;
import com.radixdlt.consensus.Vertex;
import com.radixdlt.consensus.bft.BFTValidatorSet;
import com.radixdlt.consensus.bft.View;
import com.radixdlt.engine.RadixEngine;
import com.radixdlt.engine.RadixEngineException;
import com.radixdlt.identifiers.EUID;
import com.radixdlt.middleware2.ClientAtom;
import com.radixdlt.middleware2.store.StoredCommittedCommand;
import com.radixdlt.serialization.Serialization;
import com.radixdlt.serialization.SerializationException;
import com.radixdlt.middleware2.LedgerAtom;
import com.radixdlt.ledger.VerifiedCommittedCommands;
import com.radixdlt.ledger.StateComputerLedger.StateComputer;
import com.radixdlt.store.berkeley.NextCommittedLimitReachedException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Consumer;

/**
 * Wraps the Radix Engine and emits messages based on success or failure
 */
public final class RadixEngineStateComputer implements StateComputer {

	// TODO: Refactor committed command when commit logic is re-written
	// TODO: as currently it's mostly loosely coupled logic
	public interface CommittedAtomWithResult {
		CommittedAtom getCommittedAtom();
		CommittedAtomWithResult ifSuccess(Consumer<ImmutableSet<EUID>> successConsumer);
		CommittedAtomWithResult ifError(Consumer<RadixEngineException> errorConsumer);
	}

	// TODO: Remove this temporary interface
	public interface CommittedAtomSender {
		void sendCommittedAtom(CommittedAtomWithResult committedAtomWithResult);
	}

	private final Serialization serialization;
	private final RadixEngine<LedgerAtom> radixEngine;
	private final View epochChangeView;
	private final CommittedCommandsReader committedCommandsReader;
	private final CommittedAtomSender committedAtomSender;
	private final Object lock = new Object();
	private final TreeMap<Long, StoredCommittedCommand> unstoredCommittedAtoms = new TreeMap<>();

	public RadixEngineStateComputer(
		Serialization serialization,
		RadixEngine<LedgerAtom> radixEngine,
		View epochChangeView,
		CommittedCommandsReader committedCommandsReader,
		CommittedAtomSender committedAtomSender
	) {
		if (epochChangeView.isGenesis()) {
			throw new IllegalArgumentException("Epoch change view must not be genesis.");
		}

		this.serialization = Objects.requireNonNull(serialization);
		this.radixEngine = Objects.requireNonNull(radixEngine);
		this.epochChangeView = epochChangeView;
		this.committedCommandsReader = Objects.requireNonNull(committedCommandsReader);
		this.committedAtomSender = Objects.requireNonNull(committedAtomSender);
	}

	// TODO Move this to a different class class when unstored committed atoms is fixed
	public VerifiedCommittedCommands getNextCommittedCommands(long stateVersion, int batchSize) throws NextCommittedLimitReachedException {
		// TODO: This may still return an empty list as we still count state versions for atoms which
		// TODO: never make it into the radix engine due to state errors. This is because we only check
		// TODO: validity on commit rather than on proposal/prepare.
		TreeMap<Long, StoredCommittedCommand> storedCommittedAtoms = committedCommandsReader
			.getNextCommittedCommands(stateVersion, batchSize);
		final VerifiedCommittedHeader nextProof;
		if (storedCommittedAtoms.firstEntry() != null) {
			nextProof = storedCommittedAtoms.firstEntry().getValue().getProof();
		} else {
			Entry<Long, StoredCommittedCommand> uncommittedEntry = unstoredCommittedAtoms.higherEntry(stateVersion);
			if (uncommittedEntry == null) {
				return null;
			}
			nextProof = uncommittedEntry.getValue().getProof();
		}

		synchronized (lock) {
			final long proofStateVersion = nextProof.getLedgerState().getStateVersion();
			Map<Long, StoredCommittedCommand> unstoredToReturn
				= unstoredCommittedAtoms.subMap(stateVersion, false, proofStateVersion, true);
			storedCommittedAtoms.putAll(unstoredToReturn);
		}

		return new VerifiedCommittedCommands(
			storedCommittedAtoms.values().stream().map(StoredCommittedCommand::getCommand).collect(ImmutableList.toImmutableList()),
			nextProof
		);
	}

	@Override
	public boolean prepare(Vertex vertex) {
		return vertex.getView().compareTo(epochChangeView) >= 0;
	}

	private ClientAtom mapCommand(Command command) {
		try {
			return serialization.fromDson(command.getPayload(), ClientAtom.class);
		} catch (SerializationException e) {
			return null;
		}
	}

	@Override
	public Optional<BFTValidatorSet> commit(VerifiedCommittedCommands verifiedCommittedCommands) {
		final VerifiedCommittedHeader proof = verifiedCommittedCommands.getProof();
		final ImmutableList<Command> commandsToStore = verifiedCommittedCommands.getCommands();
		long headerStateVersion = proof.getLedgerState().getStateVersion();
		for (int i = 0; i < commandsToStore.size(); i++) {
			Command command = commandsToStore.get(i);
			long stateVersion = headerStateVersion - commandsToStore.size() + i + 1;
			boolean storedInRadixEngine = false;
			final ClientAtom clientAtom = this.mapCommand(command);
			if (clientAtom != null) {
				final CommittedAtom committedAtom = new CommittedAtom(clientAtom, stateVersion, proof);
				try {
					// TODO: execute list of commands instead
					this.radixEngine.checkAndStore(committedAtom);
					storedInRadixEngine = true;
				} catch (RadixEngineException e) {
					// TODO: Don't check for state computer errors for now so that we don't
					// TODO: have to deal with failing leader proposals
					// TODO: Reinstate this when ProposalGenerator + Mempool can guarantee correct proposals

					// TODO: move VIRTUAL_STATE_CONFLICT to static check
					committedAtomSender.sendCommittedAtom(CommittedAtoms.error(committedAtom, e));
				}
			}

			if (!storedInRadixEngine) {
				StoredCommittedCommand storedCommittedCommand = new StoredCommittedCommand(
					command,
					verifiedCommittedCommands.getProof()
				);
				this.unstoredCommittedAtoms.put(stateVersion, storedCommittedCommand);
			}
		}

		if (proof.getLedgerState().isEndOfEpoch()) {
			RadixEngineValidatorSetBuilder validatorSetBuilder = this.radixEngine.getComputedState(RadixEngineValidatorSetBuilder.class);

			return Optional.of(validatorSetBuilder.build());
		}

		return Optional.empty();
	}
}
