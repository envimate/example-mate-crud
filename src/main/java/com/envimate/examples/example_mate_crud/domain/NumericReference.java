package com.envimate.examples.example_mate_crud.domain;

import com.envimate.examples.example_mate_crud.validation.NumericValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NumericReference {
    private final int value;

    public static NumericReference numericReference(final String value) {
        final int validated = NumericValidator.ensurePositiveInteger(value, "value of NumericReference");
        return new NumericReference(validated);
    }
    public String internalValue() {
        return String.valueOf(this.value);
    }
}
