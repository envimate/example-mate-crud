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

package com.envimate.examples.example_mate_crud.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.google.inject.binder.ScopedBindingBuilder;

import java.lang.reflect.Constructor;

public abstract class CrudModule extends AbstractModule {
    @Override
    protected final void configure() {
        super.configure();
        bindDependencies();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected ScopedBindingBuilder bindToSingleConstructor(final Class<?> aClass) {
        final Constructor[] constructors = aClass.getConstructors();
        if (constructors.length == 1) {
            return bind(aClass).toConstructor(constructors[0]);
        } else {
            throw new IllegalArgumentException(String.format("%s has multiple constructors", aClass));
        }
    }

    protected abstract void bindDependencies();
}
