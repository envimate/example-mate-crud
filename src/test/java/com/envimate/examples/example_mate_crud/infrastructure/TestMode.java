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

import java.util.Optional;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TestMode {
    private final String testMode;

    public static TestMode testMode() {
        final boolean fullIntegrationTestsModeActive = Optional.ofNullable(System.getenv("ENVIMATE_CRUD_ENDPOINT")).map(s -> !s.isBlank()).orElse(false);
        final boolean partialIntegrationTestsModeActive = Optional.ofNullable(System.getenv("AWS_SECRET_ACCESS_KEY")).map(s -> !s.isBlank()).orElse(false);
        if (fullIntegrationTestsModeActive) {
            return new TestMode("FullIntegrationTests");
        } else if (partialIntegrationTestsModeActive) {
            return new TestMode("PartialIntegrationTests");
        } else {
            return new TestMode("InMemoryTests");
        }
    }

    public boolean isInMemory() {
        return "InMemoryTests".equals(this.testMode);
    }

    public boolean isPartialIntegration() {
        return "PartialIntegrationTests".equals(this.testMode);
    }

    public boolean isFullIntegration() {
        return "FullIntegrationTests".equals(this.testMode);
    }
}
