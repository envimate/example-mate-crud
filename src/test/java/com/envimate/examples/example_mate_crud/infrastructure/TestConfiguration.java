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

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;
import java.util.stream.Collectors;

import static com.envimate.examples.example_mate_crud.infrastructure.TestConfiguration.EnvironmentVariables.endpoint;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TestConfiguration {

    private static String requiredEnvironmentVariable(final String name) {
        final String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
            throw new MissingEnvironmentVariable(name);
        }
        return value.trim();
    }


    public static Host host() {
        return Host.host(endpoint());
    }


    static final class EnvironmentVariables {
        private static final String ENVIMATE = "ENVIMATE";
        private static final String ENVIMATE_CRUD_ENDPOINT = ENVIMATE + "_CRUD_ENDPOINT";

        private EnvironmentVariables() {
        }

        static String endpoint() {
            return requiredEnvironmentVariable(ENVIMATE_CRUD_ENDPOINT);
        }

        static Map<String, String> currentVariables() {
            return System.getenv().entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith(ENVIMATE))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }

    private static class MissingEnvironmentVariable extends RuntimeException {
        private static final long serialVersionUID = 3072601277452904924L;

        MissingEnvironmentVariable(final String name) {
            super(name);
        }
    }
}
