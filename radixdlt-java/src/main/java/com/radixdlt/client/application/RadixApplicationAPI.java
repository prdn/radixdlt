package com.radixdlt.client.application;

import com.google.gson.JsonObject;
import com.radixdlt.client.application.actions.DataStore;
import com.radixdlt.client.application.actions.TokenTransfer;
import com.radixdlt.client.application.actions.UniqueProperty;
import com.radixdlt.client.application.objects.Data;
import com.radixdlt.client.application.objects.Data.DataBuilder;
import com.radixdlt.client.application.objects.UnencryptedData;
import com.radixdlt.client.application.translate.AddressTokenState;
import com.radixdlt.client.application.translate.DataStoreTranslator;
import com.radixdlt.client.application.translate.TokenTransferTranslator;
import com.radixdlt.client.application.translate.UniquePropertyTranslator;
import com.radixdlt.client.core.atoms.TokenReference;
import com.radixdlt.client.core.RadixUniverse;
import com.radixdlt.client.core.RadixUniverse.Ledger;
import com.radixdlt.client.core.address.RadixAddress;
import com.radixdlt.client.core.atoms.AccountReference;
import com.radixdlt.client.core.atoms.AtomBuilder;
import com.radixdlt.client.application.identity.RadixIdentity;
import com.radixdlt.client.core.atoms.particles.Minted;
import com.radixdlt.client.core.atoms.particles.TokenParticle;
import com.radixdlt.client.core.atoms.UnsignedAtom;
import com.radixdlt.client.core.atoms.particles.TokenParticle.MintPermissions;
import com.radixdlt.client.core.crypto.ECPublicKey;
import com.radixdlt.client.core.network.AtomSubmissionUpdate;
import com.radixdlt.client.core.network.AtomSubmissionUpdate.AtomSubmissionState;
import com.radixdlt.client.core.serialization.RadixJson;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.observables.ConnectableObservable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Radix Dapp API, a high level api which dapps can utilize. The class hides
 * the complexity of Atoms and cryptography and exposes a simple high level interface.
 */
public class RadixApplicationAPI {
	private static final Logger LOGGER = LoggerFactory.getLogger(RadixApplicationAPI.class);

	public static class Result {
		private final Observable<AtomSubmissionUpdate> updates;
		private final Completable completable;

		private Result(Observable<AtomSubmissionUpdate> updates) {
			this.updates = updates;

			this.completable = updates.filter(AtomSubmissionUpdate::isComplete)
				.firstOrError()
				.flatMapCompletable(update -> {
					if (update.getState() == AtomSubmissionState.STORED) {
						return Completable.complete();
					} else {
						return Completable.error(new RuntimeException(update.getData().toString()));
					}
				});
		}

		public Observable<AtomSubmissionUpdate> toObservable() {
			return updates;
		}

		public Completable toCompletable() {
			return completable;
		}
	}

	private final RadixIdentity identity;
	private final RadixUniverse universe;

	// TODO: Translators from application to particles
	private final DataStoreTranslator dataStoreTranslator;
	private final TokenTransferTranslator tokenTransferTranslator;
	private final UniquePropertyTranslator uniquePropertyTranslator;

	// TODO: Translator from particles to atom
	private final Supplier<AtomBuilder> atomBuilderSupplier;

	private final Ledger ledger;

	private RadixApplicationAPI(
		RadixIdentity identity,
		RadixUniverse universe,
		DataStoreTranslator dataStoreTranslator,
		Supplier<AtomBuilder> atomBuilderSupplier,
		Ledger ledger
	) {
		this.identity = identity;
		this.universe = universe;
		this.dataStoreTranslator = dataStoreTranslator;
		this.tokenTransferTranslator = new TokenTransferTranslator(universe, ledger.getParticleStore());
		this.uniquePropertyTranslator = new UniquePropertyTranslator();
		this.atomBuilderSupplier = atomBuilderSupplier;
		this.ledger = ledger;
	}

	public static RadixApplicationAPI create(RadixIdentity identity) {
		Objects.requireNonNull(identity);
		return create(identity, RadixUniverse.getInstance(), DataStoreTranslator.getInstance(), AtomBuilder::new);
	}

	public static RadixApplicationAPI create(
		RadixIdentity identity,
		RadixUniverse universe,
		DataStoreTranslator dataStoreTranslator,
		Supplier<AtomBuilder> atomBuilderSupplier
	) {
		Objects.requireNonNull(identity);
		Objects.requireNonNull(universe);
		Objects.requireNonNull(atomBuilderSupplier);
		return new RadixApplicationAPI(identity, universe, dataStoreTranslator, atomBuilderSupplier, universe.getLedger());
	}

	/**
	 * Idempotent method which prefetches atoms in user's account
	 * TODO: what to do when no puller available
	 *
	 * @return Disposable to dispose to stop pulling
	 */
	public Disposable pull() {
		return pull(getMyAddress());
	}

	/**
	 * Idempotent method which prefetches atoms in an address
	 * TODO: what to do when no puller available
	 *
	 * @param address the address to pull atoms from
	 * @return Disposable to dispose to stop pulling
	 */
	public Disposable pull(RadixAddress address) {
		Objects.requireNonNull(address);

		if (ledger.getAtomPuller() != null) {
			return ledger.getAtomPuller().pull(address);
		} else {
			return Disposables.disposed();
		}
	}

	public TokenReference getNativeToken() {
		return universe.getNativeToken();
	}

	public ECPublicKey getMyPublicKey() {
		return identity.getPublicKey();
	}

	public RadixIdentity getMyIdentity() {
		return identity;
	}

	public RadixAddress getMyAddress() {
		return universe.getAddressFrom(identity.getPublicKey());
	}

	public Observable<Data> getData(RadixAddress address) {
		Objects.requireNonNull(address);

		pull(address);

		return ledger.getAtomStore().getAtoms(address)
			.map(dataStoreTranslator::fromAtom)
			.flatMapMaybe(data -> data.isPresent() ? Maybe.just(data.get()) : Maybe.empty());
	}

	public Observable<UnencryptedData> getReadableData(RadixAddress address) {
		return getData(address)
			.flatMapMaybe(data -> identity.decrypt(data).toMaybe().onErrorComplete());
	}

	public Result storeData(Data data) {
		return this.storeData(data, getMyAddress());
	}

	public Result storeData(Data data, RadixAddress address) {
		DataStore dataStore = new DataStore(data, address);

		AtomBuilder atomBuilder = atomBuilderSupplier.get();
		ConnectableObservable<AtomSubmissionUpdate> updates = dataStoreTranslator.translate(dataStore, atomBuilder)
			.andThen(Single.fromCallable(
				() -> atomBuilder.buildWithPOWFee(universe.getMagic(), address.getPublicKey(), universe.getPOWToken())
			))
			.flatMap(identity::sign)
			.flatMapObservable(ledger.getAtomSubmitter()::submitAtom)
			.replay();

		updates.connect();

		return new Result(updates);
	}

	public Result storeData(Data data, RadixAddress address0, RadixAddress address1) {
		DataStore dataStore = new DataStore(data, address0, address1);

		AtomBuilder atomBuilder = atomBuilderSupplier.get();
		ConnectableObservable<AtomSubmissionUpdate> updates = dataStoreTranslator.translate(dataStore, atomBuilder)
			.andThen(Single.fromCallable(
				() -> atomBuilder.buildWithPOWFee(universe.getMagic(), address0.getPublicKey(), universe.getPOWToken())
			))
			.flatMap(identity::sign)
			.flatMapObservable(ledger.getAtomSubmitter()::submitAtom)
			.replay();

		updates.connect();

		return new Result(updates);
	}

	public Observable<TokenTransfer> getMyTokenTransfers() {
		return getTokenTransfers(getMyAddress());
	}

	public Observable<TokenTransfer> getTokenTransfers(RadixAddress address) {
		Objects.requireNonNull(address);

		pull(address);

		return ledger.getAtomStore().getAtoms(address)
			.flatMapIterable(tokenTransferTranslator::fromAtom);
	}

	public Observable<Map<TokenReference, BigDecimal>> getBalance(RadixAddress address) {
		Objects.requireNonNull(address);

		pull(address);

		return tokenTransferTranslator.getTokenState(address)
			.map(AddressTokenState::getBalance)
			.map(map -> map.entrySet().stream().collect(
				Collectors.toMap(Entry::getKey,
					e -> {
						BigDecimal subUnitAmount = BigDecimal.valueOf(e.getValue());
						BigDecimal subUnits = BigDecimal.valueOf(TokenReference.SUB_UNITS);
						return subUnitAmount.divide(subUnits, MathContext.UNLIMITED);
					})
				)
			);
	}

	public Observable<BigDecimal> getMyBalance(TokenReference tokenReference) {
		return getBalance(getMyAddress(), tokenReference);
	}

	public Observable<BigDecimal> getBalance(RadixAddress address, TokenReference token) {
		Objects.requireNonNull(token);

		return getBalance(address)
			.map(balances -> Optional.ofNullable(balances.get(token)).orElse(BigDecimal.ZERO));
	}

	// TODO: refactor to access a TokenTranslator
	public Result createFixedSupplyToken(String name, String iso, String description, long fixedSupply) {
		AccountReference account = new AccountReference(getMyPublicKey());
		TokenParticle token = new TokenParticle(account, name, iso, description, MintPermissions.SAME_ATOM_ONLY, null);
		Minted minted = new Minted(
			fixedSupply * TokenReference.SUB_UNITS,
			account,
			System.currentTimeMillis(),
			token.getTokenReference(),
			System.currentTimeMillis() / 60000L + 60000
		);

		UnsignedAtom unsignedAtom = atomBuilderSupplier.get()
			.addParticle(token)
			.addParticle(minted)
			.buildWithPOWFee(universe.getMagic(), getMyPublicKey(), universe.getPOWToken());

		ConnectableObservable<AtomSubmissionUpdate> updates = identity.sign(unsignedAtom)
			.flatMapObservable(ledger.getAtomSubmitter()::submitAtom)
			.replay();

		updates.connect();

		return new Result(updates);
	}

	/**
	 * Sends an amount of a token to an address
	 *
	 * @param to the address to send tokens to
	 * @param amount the amount and token type
	 * @return result of the transaction
	 */
	public Result sendTokens(RadixAddress to, BigDecimal amount, TokenReference token) {
		return transferTokens(getMyAddress(), to, amount, token);
	}


	/**
	 * Sends an amount of a token with a message attachment to an address
	 *
	 * @param to the address to send tokens to
	 * @param amount the amount and token type
	 * @param message message to be encrypted and attached to transfer
	 * @return result of the transaction
	 */
	public Result sendTokensWithMessage(RadixAddress to, BigDecimal amount, TokenReference token, @Nullable String message) {
		return sendTokensWithMessage(to, amount, token, message, null);
	}

	/**
	 * Sends an amount of a token with a message attachment to an address
	 *
	 * @param to the address to send tokens to
	 * @param amount the amount and token type
	 * @param message message to be encrypted and attached to transfer
	 * @return result of the transaction
	 */
	public Result sendTokensWithMessage(
		RadixAddress to,
		BigDecimal amount,
		TokenReference token,
		@Nullable String message,
		@Nullable byte[] unique
	) {
		final Data attachment;
		if (message != null) {
			attachment = new DataBuilder()
				.addReader(to.getPublicKey())
				.addReader(getMyPublicKey())
				.bytes(message.getBytes()).build();
		} else {
			attachment = null;
		}

		return transferTokens(getMyAddress(), to, amount, token, attachment, unique);
	}

	/**
	 * Sends an amount of a token with a data attachment to an address
	 *
	 * @param to the address to send tokens to
	 * @param amount the amount and token type
	 * @param attachment the data attached to the transaction
	 * @return result of the transaction
	 */
	public Result sendTokens(RadixAddress to, BigDecimal amount, TokenReference token, @Nullable Data attachment) {
		return transferTokens(getMyAddress(), to, amount, token, attachment);
	}

	/**
	 * Sends an amount of a token with a data attachment to an address with a unique property
	 * meaning that no other transaction can be executed with the same unique bytes
	 *
	 * @param to the address to send tokens to
	 * @param amount the amount and token type
	 * @param attachment the data attached to the transaction
	 * @param unique the bytes representing the unique id of this transaction
	 * @return result of the transaction
	 */
	public Result sendTokens(
		RadixAddress to,
		BigDecimal amount,
		TokenReference token,
		@Nullable Data attachment,
		@Nullable byte[] unique
	) {
		return transferTokens(getMyAddress(), to, amount, token, attachment, unique);
	}

	public Result transferTokens(RadixAddress from, RadixAddress to, BigDecimal amount, TokenReference token) {
		return transferTokens(from, to, amount, null, null);
	}

	public Result transferTokens(
		RadixAddress from,
		RadixAddress to,
		BigDecimal amount,
		TokenReference token,
		@Nullable Data attachment
	) {
		return transferTokens(from, to, amount, token, attachment, null);
	}

	public Result transferTokens(
		RadixAddress from,
		RadixAddress to,
		BigDecimal amount,
		TokenReference token,
		@Nullable Data attachment,
		@Nullable byte[] unique // TODO: make unique immutable
	) {
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);
		Objects.requireNonNull(amount);
		Objects.requireNonNull(token);

		final TokenTransfer tokenTransfer =
			TokenTransfer.create(from, to, amount, token, attachment);
		final UniqueProperty uniqueProperty;
		if (unique != null) {
			// Unique Property must be the from address so that all validation occurs in a single shard.
			// Once multi-shard validation is implemented this constraint can be removed.
			uniqueProperty = new UniqueProperty(unique, from);
		} else {
			uniqueProperty = null;
		}

		return executeTransaction(tokenTransfer, uniqueProperty);
	}

	// TODO: make this more generic
	private Result executeTransaction(TokenTransfer tokenTransfer, @Nullable UniqueProperty uniqueProperty) {
		Objects.requireNonNull(tokenTransfer);

		pull();

		AtomBuilder atomBuilder = atomBuilderSupplier.get();

		Single<UnsignedAtom> unsignedAtom =
			uniquePropertyTranslator.translate(uniqueProperty, atomBuilder)
			.andThen(tokenTransferTranslator.translate(tokenTransfer, atomBuilder))
			.andThen(Single.fromCallable(
				() -> atomBuilder.buildWithPOWFee(universe.getMagic(), tokenTransfer.getFrom().getPublicKey(), universe.getPOWToken())
			));

		ConnectableObservable<AtomSubmissionUpdate> updates =
			unsignedAtom
			.flatMap(identity::sign)
			.flatMapObservable(ledger.getAtomSubmitter()::submitAtom)
			.doOnNext(update -> {
				//TODO: retry on collision
				if (update.getState() == AtomSubmissionState.COLLISION) {
					JsonObject data = update.getData().getAsJsonObject();
					String jsonPointer = data.getAsJsonPrimitive("pointerToConflict").getAsString();
					LOGGER.info("ParticleConflict: pointer({}) cause({}) atom({})",
						jsonPointer,
						data.getAsJsonPrimitive("cause").getAsString(),
						RadixJson.getGson().toJson(update.getAtom())
					);
				}
			})
			.replay();

		updates.connect();

		return new Result(updates);
	}
}
