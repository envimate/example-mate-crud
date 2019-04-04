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

package com.envimate.examples.example_mate_crud.validation;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.envimate.examples.example_mate_crud.validation.CustomTypeValidationException.customTypeValidationException;

@ToString
@EqualsAndHashCode
public final class LengthValidator {
    private LengthValidator() {
    }

    public static String ensureLength(final String value,
                                      final Integer minLength, final Integer maxLength,
                                      final String description) {
        final String sanitized = SanityValidator.sanitized(value, description);
        if (sanitized.length() > maxLength) {
            throw customTypeValidationException("%s too long, max %s characters allowed.",
                    description,
                    maxLength);
        }
        if (sanitized.length() < minLength) {
            throw customTypeValidationException("%s too short, min %s characters required.",
                    description,
                    minLength);
        }
        return sanitized;
    }

    public static String ensureMinLength(final String value, final Integer minLength, final String description) {
        final String sanitized = SanityValidator.sanitized(value, description);
        if (sanitized.length() < minLength) {
            throw customTypeValidationException("%s too short, min %s characters required.",
                    description,
                    minLength);
        }
        return sanitized;
    }

    public static String ensureMaxLength(final String value, final Integer maxLength, final String description) {
        final String sanitized = SanityValidator.sanitized(value, description);
        if (sanitized.length() > maxLength) {
            throw customTypeValidationException("%s too long, max %s characters allowed.",
                    description,
                    maxLength);
        }
        return sanitized;
    }
}
