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

package com.envimate.examples.example_mate_crud.usecases.payment.fetch;

import com.envimate.examples.example_mate_crud.domain.Id;
import com.envimate.examples.example_mate_crud.domain.repository.ResourceRepository;
import com.envimate.examples.example_mate_crud.usecases.Resource;
import com.envimate.examples.example_mate_crud.usecases.payment.ResourceNotFoundException;

public final class FetchResource {
    private final ResourceRepository resourceRepository;

    public FetchResource(final ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource fetchResource(final Id id) {
        final Resource resource = this.resourceRepository.find(id);
        if (resource == null) {
            throw ResourceNotFoundException.resourceNotFoundException("Resource for id %s not found", id.internalValue());
        }

        return resource;
    }
}
