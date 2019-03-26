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

import com.envimate.examples.example_mate_crud.domain.ResourceType;
import com.envimate.examples.example_mate_crud.infrastructure.Backend;
import com.envimate.examples.example_mate_crud.infrastructure.Scenario;
import com.envimate.examples.example_mate_crud.usecases.resource.create.CreateResource;
import com.envimate.examples.example_mate_crud.usecases.resource.create.CreateResourceDTO;
import com.envimate.examples.example_mate_crud.usecases.resource.create.CreateResourceRequest;
import com.envimate.examples.example_mate_crud.usecases.resource.list.ListResource;
import com.envimate.examples.example_mate_crud.usecases.resource.list.ListResourceDTO;
import com.envimate.examples.example_mate_crud.usecases.resource.list.ListResourceRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.envimate.examples.example_mate_crud.infrastructure.Scenario.scenario;
import static com.envimate.examples.example_mate_crud.usecases.resource.create.CreateResourceRequest.createResourceRequest;

public interface ListTestCase {

    @Test
    default void listUseCaseEmptyDatabase(final Backend backend) {
        final Scenario<ListResourceDTO, ListResourceRequest> scenario =
                scenario(ListResource.class, ListResourceDTO.class, null);

        backend.verifyThat(scenario)
                .isSuccess()
                .verifyResponse(
                        listResourceDTO -> listResourceDTO.data.isEmpty(), "List is expected to be empty"
                );
    }

    @Test
    default void listUseCase(final Backend backend) {
        final CreateResourceRequest createResourceRequest = createResourceRequest(ResourceType.resourceType("payment"));
        final Scenario<CreateResourceDTO, CreateResourceRequest> existing =
                scenario(CreateResource.class, CreateResourceDTO.class, createResourceRequest);

        final Scenario<ListResourceDTO, ListResourceRequest> scenario =
                scenario(ListResource.class, ListResourceDTO.class, null);

        backend.given(List.of(existing))
                .verifyThat(scenario)
                .isSuccess()
                .verifyResponse(
                        listResourceDTO -> listResourceDTO.data.size() == 1,
                        "List is expected to contain the previously created resource."
                );
    }

}
