package com.envimate.examples.example_mate_crud.domain;

import com.envimate.examples.example_mate_crud.validation.LengthValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentId {
    private final String value;

    public static PaymentId paymentId(final String value) {
        final String validated = LengthValidator.ensureMinLength(value, 1, "value of PaymentId");
        return new PaymentId(validated);
    }
    public String internalValue() {
        return this.value;
    }
}
