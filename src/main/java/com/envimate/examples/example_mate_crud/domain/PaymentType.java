package com.envimate.examples.example_mate_crud.domain;

import com.envimate.examples.example_mate_crud.validation.LengthValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentType {
    private final String value;

    public static PaymentType paymentType(final String value) {
        final String validated = LengthValidator.ensureMinLength(value, 1, "value of PaymentType");
        return new PaymentType(validated);
    }
    public String internalValue() {
        return this.value;
    }
}
