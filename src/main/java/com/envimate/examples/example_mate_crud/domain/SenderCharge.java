package com.envimate.examples.example_mate_crud.domain;

import com.envimate.examples.example_mate_crud.validation.LengthValidator;
import com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator.ensureNotNull;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SenderCharge {
    public final Amount amount;
    public final Currency currency;

    public static SenderCharge senderCharge(final Amount amount,
                                            final Currency currency) {
        ensureNotNull(amount, "Amount of SenderCharge");
        ensureNotNull(currency, "Currency of SenderCharge");
        return new SenderCharge(amount, currency);
    }
}
