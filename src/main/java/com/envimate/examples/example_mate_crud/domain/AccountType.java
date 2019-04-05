package com.envimate.examples.example_mate_crud.domain;

import com.envimate.examples.example_mate_crud.validation.NumericValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountType {
    private final int value;

    public static AccountType accountType(final String value) {
        final int validated = NumericValidator.ensurePositiveInteger(value, "value of AccountType");
        return new AccountType(validated);
    }

    public String internalValue() {
        return String.valueOf(this.value);
    }
}
