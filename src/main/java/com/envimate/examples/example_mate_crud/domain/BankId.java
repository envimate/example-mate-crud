package com.envimate.examples.example_mate_crud.domain;

import com.envimate.examples.example_mate_crud.validation.LengthValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BankId {
    private final String value;

    public static BankId bankId(final String value) {
        final String validated = LengthValidator.ensureMinLength(value, 1, "value of BankId");
        return new BankId(validated);
    }

    public String internalValue() {
        return this.value;
    }
}
