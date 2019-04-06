/*
 * Copyright (c) 2018 envimate GmbH - https://envimate.com/.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.envimate.examples.example_mate_crud.infrastructure.endpoints;

import com.envimate.httpmate.HttpMate;
import com.envimate.httpmate.request.RawHttpRequest;
import com.envimate.httpmate.response.HttpResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.envimate.httpmate.request.RawHttpRequest.rawHttpRequest;
import static com.envimate.httpmate.util.Streams.streamInputStreamToOutputStream;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class PureJavaEndpointHandler implements HttpHandler {
    private final HttpMate httpMate;

    static HttpHandler javaOnlyEndpointHandler(final HttpMate httpMate) {
        return new PureJavaEndpointHandler(httpMate);
    }

    private static Map<String, String> queryToMap(final String query) {
        final Map<String, String> result = new HashMap<>();
        if (query == null) {
            return result;
        }
        for (final String param : query.split("&")) {
            final String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }

    @Override
    public void handle(final HttpExchange exchange) throws IOException {
        final String requestMethod = exchange.getRequestMethod();
        final String path = exchange.getRequestURI().getPath();
        final String query = exchange.getRequestURI().getQuery();
        final Map<String, String> queryParameters = queryToMap(query);
        final Map<String, String> headers = exchange.getRequestHeaders().entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> entry.getValue().get(0)));
        final InputStream body = exchange.getRequestBody();
        final RawHttpRequest rawHttpRequest = rawHttpRequest(requestMethod, path, queryParameters, headers, body);
        final HttpResponse response = this.httpMate.handleRequest(rawHttpRequest);
        response.headers().forEach((key, value) -> exchange.getResponseHeaders().put(key, singletonList(value)));
        exchange.sendResponseHeaders(response.status(), 0);
        final OutputStream responseStream = exchange.getResponseBody();
        streamInputStreamToOutputStream(response.body(), responseStream);
    }
}
