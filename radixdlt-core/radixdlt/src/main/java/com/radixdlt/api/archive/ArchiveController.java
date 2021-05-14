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

package com.radixdlt.api.archive;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;
import com.radixdlt.api.Controller;
import com.radixdlt.api.Controller;
import com.radixdlt.api.archive.qualifier.Archive;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;

import static com.radixdlt.api.RestUtils.respond;
import static com.radixdlt.api.RestUtils.withBodyAsync;

public final class ArchiveController implements Controller {
	private final JsonRpcServer jsonRpcServer;

	@Inject
	public ArchiveController(@Archive JsonRpcServer jsonRpcServer) {
		this.jsonRpcServer = jsonRpcServer;
	}

	@Override
	public void configureRoutes(RoutingHandler handler) {
		handler.post("/archive", this::handleRpc);
		handler.post("/archive/", this::handleRpc);
	}

	@VisibleForTesting
	void handleRpc(HttpServerExchange exchange) {
		withBodyAsync(exchange, request -> respond(exchange, jsonRpcServer.handle(request)));
	}
}
