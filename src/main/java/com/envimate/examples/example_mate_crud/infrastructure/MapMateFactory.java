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

import com.envimate.examples.example_mate_crud.validation.CustomTypeValidationException;
import com.envimate.mapmate.deserialization.Deserializer;
import com.envimate.mapmate.filters.ClassFilters;
import com.envimate.mapmate.serialization.Serializer;
import com.envimate.mapmate.validation.ExceptionMappingWithPropertyPath;
import com.envimate.mapmate.validation.ValidationError;
import com.google.gson.Gson;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class MapMateFactory {
    private static final String COM_ENVIMATE_EXAMPLES_EXAMPLE_MATE_CRUD_CUSTOM_TYPES =
            "com.envimate.examples.example_mate_crud.domain";
    private static final String COM_ENVIMATE_EXAMPLES_EXAMPLE_MATE_CRUD_DTOS =
            "com.envimate.examples.example_mate_crud.usecases";

    private MapMateFactory() {
    }

    public static Serializer serializer() {
        return Serializer.aSerializer()
                .thatScansThePackage(COM_ENVIMATE_EXAMPLES_EXAMPLE_MATE_CRUD_CUSTOM_TYPES)
                .forCustomPrimitives()
                .filteredBy(ClassFilters.allClassesThatHaveAPublicStringMethodWithZeroArgumentsNamed("internalValue"))
                .thatAre().serializedUsingTheMethodNamed("internalValue")
                .thatScansThePackage(COM_ENVIMATE_EXAMPLES_EXAMPLE_MATE_CRUD_DTOS)
                .forDataTransferObjects()
                .filteredBy(ClassFilters.includingAll())
                .thatAre().serializedByItsPublicFields()
                .withMarshaller(new Gson()::toJson)
                .build();
    }

    public static Deserializer deserializer() {
        return Deserializer.aDeserializer()
                .thatScansThePackage(COM_ENVIMATE_EXAMPLES_EXAMPLE_MATE_CRUD_CUSTOM_TYPES)
                .forCustomPrimitives()
                .filteredBy(ClassFilters.allClassesThatHaveAPublicStringMethodWithZeroArgumentsNamed("internalValue"))
                .thatAre().deserializedUsingTheStaticMethodWithSingleStringArgument()
                .thatScansThePackage(COM_ENVIMATE_EXAMPLES_EXAMPLE_MATE_CRUD_DTOS)
                .forDataTransferObjects()
                .filteredBy(ClassFilters.includingAll())
                .thatAre().deserializedUsingTheSingleFactoryMethod()
                .withUnmarshaller(new Gson()::fromJson)
                .mappingExceptionUsing(CustomTypeValidationException.class, new ExceptionMappingWithPropertyPath() {
                    @Override
                    public ValidationError map(final Throwable t, final String propertyPath) {
                        return new ValidationError(t.getMessage(), propertyPath);
                    }
                })
                .build();
    }
}
