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

package com.envimate.examples.example_mate_crud.usecases.payment.update;

import com.envimate.examples.example_mate_crud.domain.Id;
import com.envimate.examples.example_mate_crud.domain.OrganisationId;
import com.envimate.examples.example_mate_crud.domain.ResourceType;
import com.envimate.examples.example_mate_crud.domain.Version;
import com.envimate.examples.example_mate_crud.usecases.payment.Attributes;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator.ensureNotNull;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UpdateResourceRequest {
    public final Id id;
    public final ResourceType resourceType;
    public final Version version;
    public final OrganisationId organisationId;
    public final Attributes attributes;

    public static UpdateResourceRequest updateResourceRequest(final Id id,
                                                              final ResourceType resourceType,
                                                              final Version version,
                                                              final OrganisationId organisationId,
                                                              final Attributes attributes
    ) {
        ensureNotNull(id, "Id in UpdateResourceRequest");
        ensureNotNull(resourceType, "ResourceType in UpdateResourceRequest");
        ensureNotNull(version, "Version in UpdateResourceRequest");
        ensureNotNull(organisationId, "OrganisationId in UpdateResourceRequest");
        ensureNotNull(attributes, "Attributes in UpdateResourceRequest");
        return new UpdateResourceRequest(id, resourceType, version, organisationId, attributes);
    }
}