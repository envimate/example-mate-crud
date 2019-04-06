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

package com.envimate.examples.example_mate_crud.infrastructure.raw_request;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.json.JSONObject;

import java.util.Map;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ApiRequest {
    public final String urlTemplate;
    public final Map<String, String> routeParameters;
    public final Map<String, String> headers;
    public final String httpMethod;
    public JSONObject body;

    public static ApiRequest apiRequest(final String urlTemplate, final Map<String, String> routeParameters, final String httpMethod) {
        return new ApiRequest(urlTemplate, routeParameters, Map.of("Content-Type", "application/json"), httpMethod);
    }

    public static ApiRequest fetchResourceRequest(final String id) {
        return apiRequest("/api/resource/{id}", Map.of("id", id), "GET");
    }


    public static ApiRequest createResourceRequest() {
        return apiRequest("/api/resource", Map.of(), "POST");
    }


    public static ApiRequest listResourceRequest() {
        return apiRequest("/api/resource", Map.of(), "GET");
    }

    public static ApiRequest updateResourceRequest(final String id) {
        return apiRequest("/api/resource/{id}", Map.of("id", id), "PUT");
    }

    public ApiRequest with(final ApiRequestBodyPropertyProvider propertyProvider) {
        if (this.body == null) {
            this.body = new JSONObject();
        }
        propertyProvider.provide(this.body);
        return this;
    }

    public boolean hasBody() {
        return body != null;
    }
}
