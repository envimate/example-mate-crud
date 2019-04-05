package com.envimate.examples.example_mate_crud.domain;

import com.envimate.examples.example_mate_crud.validation.LengthValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountName {
    private final String value;

    public static AccountName accountName(final String value) {
        final String validated = LengthValidator.ensureMinLength(value, 2, "value of AccountName");
        return new AccountName(validated);
    }

    public String internalValue() {
        return this.value;
    }
}
