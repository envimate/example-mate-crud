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

package com.envimate.examples.example_mate_crud.infrastructure.inmemory;

import com.envimate.examples.example_mate_crud.domain.Id;
import com.envimate.examples.example_mate_crud.domain.Resource;
import com.envimate.examples.example_mate_crud.domain.ResourceType;
import com.envimate.examples.example_mate_crud.domain.Version;
import com.envimate.examples.example_mate_crud.domain.repository.ResourceRepository;
import com.envimate.mapmate.deserialization.Deserializer;
import com.envimate.mapmate.serialization.Serializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ResourceInMemoryRepository implements ResourceRepository {
    private static final int INITIAL_CAPACITY = 100;

    private final Serializer serializer;
    private final Deserializer deserializer;
    private final Map<Id, Resource> db;

    public ResourceInMemoryRepository(final Serializer serializer,
                                      final Deserializer deserializer) {
        this.serializer = serializer;
        this.deserializer = deserializer;
        this.db = new HashMap<>(INITIAL_CAPACITY);
    }

    @Override
    public List<Resource> all() {
        return new ArrayList<>(this.db.values());
    }

    public void create(final Resource resource) {
        this.db.put(resource.id, clone(resource));
    }

    @Override
    public Resource find(final Id id) {
        return db.get(id);
    }

    @Override
    public Resource update(final Id id, final Version version, final ResourceType resourceType) {
        final Resource oldResource = db.get(id);
        if(oldResource == null) {
            throw new UnsupportedOperationException("//TODO");
        }
        //todo next?...
//        final Resource newResource = Resource.resource(id, resourceType, Version.next(version), organisationId);
//        db.put(id, newResource);
        return null;
    }

    private Resource clone(final Resource resource) {
        return this.deserializer.deserialize(this.serializer.serialize(resource), Resource.class);
    }
}
