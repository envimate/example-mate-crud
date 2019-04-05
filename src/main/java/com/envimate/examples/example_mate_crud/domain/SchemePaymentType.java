package com.envimate.examples.example_mate_crud.domain;

import com.envimate.examples.example_mate_crud.validation.LengthValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SchemePaymentType {
    private final String value;

    public static SchemePaymentType schemePaymentType(final String value) {
        final String validated = LengthValidator.ensureMinLength(value, 1, "value of SchemePaymentType");
        return new SchemePaymentType(validated);
    }

}
