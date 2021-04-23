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

package org.radix.api.jsonrpc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.radix.api.jsonrpc.JsonRpcUtil.RpcError;
import org.radix.api.jsonrpc.handler.NetworkHandler;
import org.radix.api.jsonrpc.handler.SystemHandler;

import com.google.common.io.CharStreams;
import com.google.inject.Inject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.undertow.server.HttpServerExchange;

import static org.radix.api.jsonrpc.JsonRpcUtil.errorResponse;
import static org.radix.api.jsonrpc.JsonRpcUtil.invalidParamsError;
import static org.radix.api.jsonrpc.JsonRpcUtil.jsonObject;
import static org.radix.api.jsonrpc.JsonRpcUtil.parseError;

/**
 * Stateless Json Rpc 2.0 Server
 */
public final class RadixJsonRpcServer {
	private static final long DEFAULT_MAX_REQUEST_SIZE = 1024L * 1024L;
	private static final Logger log = LogManager.getLogger();

	/**
	 * Maximum request size in bytes
	 */
	private final long maxRequestSizeBytes;

	/**
	 * Store to query atoms from
	 */
	private final Map<String, JsonRpcHandler> handlers = new HashMap<>();
	private final SystemHandler systemHandler;
	private final NetworkHandler networkHandler;

	@Inject
	public RadixJsonRpcServer(
		SystemHandler systemHandler,
		NetworkHandler networkHandler,
		Map<String, JsonRpcHandler> additionalHandlers
	) {
		this(systemHandler, networkHandler, additionalHandlers, DEFAULT_MAX_REQUEST_SIZE);
	}

	public RadixJsonRpcServer(
		SystemHandler systemHandler,
		NetworkHandler networkHandler,
		Map<String, JsonRpcHandler> additionalHandlers,
		long maxRequestSizeBytes
	) {
		this.systemHandler = systemHandler;
		this.networkHandler = networkHandler;
		this.maxRequestSizeBytes = maxRequestSizeBytes;

		fillHandlers(additionalHandlers);
	}

	private void fillHandlers(Map<String, JsonRpcHandler> additionalHandlers) {
		//General info
		handlers.put("Universe.getUniverse", systemHandler::handleGetUniverse);
		handlers.put("Network.getInfo", systemHandler::handleGetLocalSystem);
		handlers.put("Ping", systemHandler::handlePing);

		//Network info
		handlers.put("Network.getLivePeers", networkHandler::handleGetLivePeers);
		handlers.put("Network.getPeers", networkHandler::handleGetPeers);

		handlers.putAll(additionalHandlers);

		handlers.keySet().forEach(name -> log.trace("Registered JSON RPC method: {}", name));
	}

	/**
	 * Extract a JSON RPC API request from an HttpServerExchange, handle it as usual and return the response
	 *
	 * @param exchange The JSON RPC API request
	 *
	 * @return The response
	 */
	public String handleJsonRpc(HttpServerExchange exchange) {
		try {
			return handleRpc(readBody(exchange)).toString();
		} catch (IOException e) {
			throw new IllegalStateException("RPC failed", e);
		}
	}

	private String readBody(HttpServerExchange exchange) throws IOException {
		// Switch to blocking since we need to retrieve whole request body
		exchange.setMaxEntitySize(maxRequestSizeBytes);
		exchange.startBlocking();

		return CharStreams.toString(new InputStreamReader(exchange.getInputStream(), StandardCharsets.UTF_8));
	}

	/**
	 * Handle the string JSON-RPC request with size checks, return appropriate error if size exceeds the limit.
	 *
	 * @param requestString The string JSON-RPC request
	 *
	 * @return The response to the request, could be a JSON-RPC error
	 */
	JSONObject handleRpc(String requestString) {
		log.trace("RPC: input {}", requestString);

		int length = requestString.getBytes(StandardCharsets.UTF_8).length;

		if (length > maxRequestSizeBytes) {
			return requestTooLongError(length);
		}

		return jsonObject(requestString)
			.map(this::handle)
			.map(value -> logValue("result", value))
			.fold(failure -> parseError("Unable to parse input: " + failure.message()), v -> v);
	}

	private JSONObject handle(JSONObject request) {
		if (!request.has("id")) {
			return invalidParamsError("The 'id' missing");
		}

		if (!request.has("method")) {
			return invalidParamsError("The method name is missing");
		}

		try {
			return Optional.ofNullable(handlers.get(logValue("method", request.getString("method"))))
				.map(handler -> handler.execute(request))
				.orElseGet(() -> errorResponse(request, RpcError.METHOD_NOT_FOUND, "Method not found"));

		} catch (Exception e) {
			logValue("Exception while handling request: ", e.getMessage());

			if (request.has("params") && request.get("params") instanceof JSONArray) {
				return errorResponse(request, RpcError.SERVER_ERROR, e.getMessage(), request.getJSONObject("params"));
			} else {
				return errorResponse(request, RpcError.SERVER_ERROR, e.getMessage());
			}
		}
	}

	private JSONObject requestTooLongError(int length) {
		var message = "request too big: " + length + " > " + maxRequestSizeBytes;

		log.trace("RPC error: {}", message);
		return errorResponse(RpcError.REQUEST_TOO_LONG, message);
	}

	private static <T> T logValue(String message, T value) {
		log.trace("RPC: {} {}", message, value);
		return value;
	}
}
