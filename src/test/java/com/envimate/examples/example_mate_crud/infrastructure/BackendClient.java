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

package com.envimate.examples.example_mate_crud.infrastructure;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BackendClient {
    private final Host host;

    public static BackendClient backendClient(final Host host) {
        return new BackendClient(host);
    }

    public RawResponse execute(final RawRequest rawRequest) {
        final String httpMethod = rawRequest.httpMethod;
        final String url = host.internalValue() + rawRequest.url;
        final HttpRequest httpRequest;
        if ("GET".equals(httpMethod)) {
            httpRequest = Unirest.get(url);
        } else if ("POST".equalsIgnoreCase(httpMethod)) {
            httpRequest = Unirest.post(url);
        } else if ("DELETE".equalsIgnoreCase(httpMethod)) {
            httpRequest = Unirest.delete(url);
        } else {
            throw new UnsupportedOperationException("Only GET and POST http methods are supported at this point");
        }

        httpRequest.headers(rawRequest.headers);
        rawRequest.routeParameters.forEach(httpRequest::routeParam);

        if(httpRequest instanceof HttpRequestWithBody) {
            ((HttpRequestWithBody)httpRequest).body(rawRequest.body);
        } else {
            if(rawRequest.hasBody()) {
                throw new UnsupportedOperationException(
                        String.format("Can't send http request with method %s and body %s",
                                rawRequest.httpMethod,
                                rawRequest.body)
                );
            }
        }

        final HttpResponse<String> stringHttpResponse;

        try {
            stringHttpResponse = httpRequest.asString();
        } catch (UnirestException e) {
            throw new UnsupportedOperationException(String.format("Error executing request %s", rawRequest), e);
        }

        return RawResponse.builder()
                .body(stringHttpResponse.getBody())
                .responseHeaders(stringHttpResponse.getHeaders())
                .statusCode(stringHttpResponse.getStatus()).build();
    }
}
