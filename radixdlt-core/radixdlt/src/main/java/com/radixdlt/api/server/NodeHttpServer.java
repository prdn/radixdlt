/*
 * (C) Copyright 2021 Radix DLT Ltd
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
 *
 */

package com.radixdlt.api.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.radixdlt.api.Controller;
import com.radixdlt.api.qualifier.AtNode;
import com.radixdlt.properties.RuntimeProperties;

import java.util.Set;

@Singleton
public final class NodeHttpServer extends AbstractHttpServer {
	private static final int DEFAULT_PORT = 3333;

	@Inject
	public NodeHttpServer(@AtNode Set<Controller> controllers, RuntimeProperties properties) {
		super(controllers, properties, "node", DEFAULT_PORT);
	}
}
