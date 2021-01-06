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

package com.radixdlt.atomos;

/**
 * Radix-style smart-contract-like entrypoint for application constraints.
 */
public interface ConstraintScrypt {

	/**
	 * Entrypoint to the constraint scrypt's logic. With the given
	 * AtomOS interface the smart constraint adds custom
	 * application constraints on top of the ledger
	 *
	 * @param os interface to the system calls to configure constraints on the ledger
	 */
	void main(SysCalls os);
}
