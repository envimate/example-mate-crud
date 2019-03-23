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

package com.envimate.examples.example_mate_crud.testcases;

import com.envimate.examples.example_mate_crud.domain.ListResourceDTO;
import com.envimate.examples.example_mate_crud.domain.ListResourceRequest;
import com.envimate.examples.example_mate_crud.infrastructure.Backend;
import com.envimate.examples.example_mate_crud.infrastructure.LocalBackendParameterResolver;
import com.envimate.examples.example_mate_crud.infrastructure.Scenario;
import com.envimate.examples.example_mate_crud.usecases.ListResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.envimate.examples.example_mate_crud.domain.ListResourceRequest.listResourceRequest;
import static com.envimate.examples.example_mate_crud.infrastructure.Scenario.scenario;

@ExtendWith(LocalBackendParameterResolver.class)
public class ListTestCase {

    @Test
    void listUseCase(final Backend backend) {
        final ListResourceRequest request = listResourceRequest();
        final Scenario<ListResourceDTO, ListResourceRequest> scenario =
                scenario(ListResource.class, ListResourceDTO.class, request);

        backend.verifyThat(scenario).isSuccess();
    }

}
