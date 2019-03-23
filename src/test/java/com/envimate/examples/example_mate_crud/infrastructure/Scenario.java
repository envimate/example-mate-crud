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

import com.envimate.examples.example_mate_crud.domain.RouteParameters;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Scenario<R, P> {
    private final Class<?> useCaseClass;
    private final Class<R> resultClass;
    private final P payload;
    private final RouteParameters routeParameters;

    public static <R, P> Scenario<R, P> scenario(final Class<?> useCaseClass,
                                                 final Class<R> resultClass,
                                                 final P payload,
                                                 final RouteParameters routeParameters) {
        return new Scenario<>(useCaseClass, resultClass, payload, routeParameters);
    }

    public static <R, P> Scenario<R, P> scenario(final Class<?> useCaseClass,
                                                 final Class<R> resultClass,
                                                 final P payload) {
        return new Scenario<>(useCaseClass, resultClass, payload, RouteParameters.empty());
    }

    P payload() {
        return this.payload;
    }

    public Class<?> useCaseClass() {
        return this.useCaseClass;
    }

    public Class<R> responseClass() {
        return resultClass;
    }

    public RouteParameters routeParameters() {
        return this.routeParameters;
    }
}
