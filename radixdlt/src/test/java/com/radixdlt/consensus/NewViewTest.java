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

package com.radixdlt.consensus;

import com.radixdlt.consensus.bft.BFTNode;
import com.radixdlt.consensus.bft.View;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class NewViewTest {
	private BFTNode author;
	private NewView testObject;
	private View view;

	@Before
	public void setUp() {
		this.view = View.of(1L);
		this.author = mock(BFTNode.class);
		this.testObject = new NewView(author, view, mock(QuorumCertificate.class), mock(QuorumCertificate.class), null);
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(NewView.class)
			.verify();
	}

	@Test
	public void testGetters() {
		assertEquals(this.view, this.testObject.getView());
		assertEquals(this.author, this.testObject.getAuthor());
	}

}
