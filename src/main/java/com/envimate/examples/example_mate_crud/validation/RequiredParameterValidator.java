package com.envimate.examples.example_mate_crud.validation;

import java.lang.reflect.Array;
import java.util.Map;

import static com.envimate.examples.example_mate_crud.validation.CustomTypeValidationException.customTypeValidationException;

public final class RequiredParameterValidator {
    private RequiredParameterValidator() {
    }

    public static void ensureNotNull(final Object value, final String description) {
        if (value == null) {
            throw customTypeValidationException("%s is required.", description);
        }
    }
    public static void ensureArrayNotEmpty(final Object[] value, final String description) {
        if (value == null) {
            throw customTypeValidationException("%s is required.", description);
        }
        if(value.length < 1) {
            throw customTypeValidationException("%s must not be empty.", description);
        }
    }
}
