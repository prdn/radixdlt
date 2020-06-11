/*
 * (C) Copyright 2020 Radix DLT Ltd
 *
 * Radix DLT Ltd licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the
 * License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.  See the License for the specific
 * language governing permissions and limitations under the License.
 */

package com.radixdlt.mempool;

import com.radixdlt.engine.RadixEngineException;
import com.radixdlt.middleware2.ClientAtom;
import com.radixdlt.middleware2.LedgerAtom;
import com.radixdlt.middleware2.converters.AtomToClientAtomConverter;
import com.radixdlt.middleware2.converters.AtomConversionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.stubbing.Answer;
import org.radix.atoms.events.AtomExceptionEvent;
import org.radix.events.Events;
import com.radixdlt.identifiers.AID;
import com.radixdlt.atommodel.Atom;
import com.radixdlt.constraintmachine.DataPointer;
import com.radixdlt.engine.RadixEngine;
import com.radixdlt.serialization.Serialization;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

public class SubmissionControlImplTest {

	private Mempool mempool;
	private RadixEngine<LedgerAtom> radixEngine;
	private Serialization serialization;
	private Events events;
	private SubmissionControlImpl submissionControl;
	private AtomToClientAtomConverter converter;

	@Before
	public void setUp() {
		this.mempool = throwingMock(Mempool.class);
		// No type check issues with mocking generic here
		@SuppressWarnings("unchecked")
		RadixEngine<LedgerAtom> re = throwingMock(RadixEngine.class);
		this.radixEngine = re;
		this.serialization = throwingMock(Serialization.class);
		this.events = throwingMock(Events.class);
		this.converter = mock(AtomToClientAtomConverter.class);
		this.submissionControl = new SubmissionControlImpl(this.mempool, this.radixEngine, this.serialization, this.events, this.converter);
	}

	@Test
	public void when_radix_engine_returns_error__then_event_is_broadcast() throws Exception {
		RadixEngineException e = mock(RadixEngineException.class);
		when(e.getDataPointer()).thenReturn(DataPointer.ofAtom());
		doThrow(e).when(this.radixEngine).staticCheck(any());
		doNothing().when(this.events).broadcast(any());

		ClientAtom atom = mock(ClientAtom.class);
		this.submissionControl.submitAtom(atom);

		verify(this.events, times(1)).broadcast(ArgumentMatchers.any(AtomExceptionEvent.class));
		verify(this.mempool, never()).addAtom(any());
	}

	@Test
	public void when_radix_engine_returns_ok__then_atom_is_added_to_mempool() throws Exception {
		doNothing().when(this.radixEngine).staticCheck(any());
		doNothing().when(this.mempool).addAtom(any());

		ClientAtom atom = mock(ClientAtom.class);
		this.submissionControl.submitAtom(atom);

		verify(this.events, never()).broadcast(any());
		verify(this.mempool, times(1)).addAtom(eq(atom));
	}

	@Test
	public void when_conversion_fails_then_exception_is_broadcasted_and_atom_is_not_added_to_mempool() throws Exception {
		doNothing().when(this.events).broadcast(any());

		JSONObject atomJson = mock(JSONObject.class);
		Atom atom = mock(Atom.class);
		AID aid = mock(AID.class);
		when(atom.getAID()).thenReturn(aid);
		doReturn(atom).when(this.serialization).fromJsonObject(eq(atomJson), eq(Atom.class));
		when(converter.convert(eq(atom))).thenThrow(mock(AtomConversionException.class));
		// No type check issues with mocking generic here
		@SuppressWarnings("unchecked")
		Consumer<ClientAtom> callback = mock(Consumer.class);
		this.submissionControl.submitAtom(atomJson, callback);
		verify(this.events, times(1)).broadcast(argThat(AtomExceptionEvent.class::isInstance));
		verify(this.mempool, never()).addAtom(any());
	}

	@Test
	public void if_deserialisation_fails__then_callback_is_not_called() throws Exception {
		doThrow(new IllegalArgumentException()).when(this.serialization).fromJsonObject(any(), any());

		AtomicBoolean called = new AtomicBoolean(false);

		try {
			this.submissionControl.submitAtom(mock(JSONObject.class, illegalStateAnswer()), a -> called.set(true));
			fail();
		} catch (IllegalArgumentException e) {
			assertThat(called.get(), is(false));
			verify(this.events, never()).broadcast(any());
			verify(this.mempool, never()).addAtom(any());
		}
	}

	@Test
	public void after_json_deserialised__then_callback_is_called_and_aid_returned()
		throws Exception {
		doNothing().when(this.radixEngine).staticCheck(any());
		Atom atomMock = throwingMock(Atom.class);
		doReturn(AID.ZERO).when(atomMock).getAID();
		doReturn(atomMock).when(this.serialization).fromJsonObject(any(), any());
		doNothing().when(this.mempool).addAtom(any());


		ClientAtom clientAtom = mock(ClientAtom.class);
		when(clientAtom.getAID()).thenReturn(AID.ZERO);
		when(converter.convert(eq(atomMock))).thenReturn(clientAtom);
		// No type check issues with mocking generic here
		@SuppressWarnings("unchecked")
		Consumer<ClientAtom> callback = mock(Consumer.class);
		this.submissionControl.submitAtom(throwingMock(JSONObject.class), callback);

		verify(callback, times(1)).accept(argThat(a -> a.getAID().equals(AID.ZERO)));
		verify(this.events, never()).broadcast(any());
		verify(this.mempool, times(1)).addAtom(any());
	}

	@Test
	public void sensible_tostring() {
		String tostring = this.submissionControl.toString();
		assertThat(tostring, containsString(this.submissionControl.getClass().getSimpleName()));
	}

    private static <T> T throwingMock(Class<T> classToMock) {
    	return mock(classToMock, illegalStateAnswer());
    }


	private static <T> Answer<T> illegalStateAnswer() {
		return inv -> {
			throw new IllegalStateException("Called unstubbed method");
		};
	}
}
