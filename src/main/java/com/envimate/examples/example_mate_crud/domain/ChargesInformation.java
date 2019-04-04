package com.envimate.examples.example_mate_crud.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator.ensureArrayNotEmpty;
import static com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator.ensureNotNull;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChargesInformation {
    public final BearerCode bearerCode;
    public final SenderCharge[] senderCharges;
    public final Amount receiverChargesAmount;
    public final Currency receiverChargesCurrency;

    public static ChargesInformation chargesInformation(
            final BearerCode bearerCode,
            final SenderCharge[] senderCharges,
            final Amount receiverChargesAmount,
            final Currency receiverChargesCurrency) {
        ensureNotNull(bearerCode,"bearerCode in ChargesInformation");
        ensureNotNull(receiverChargesAmount,"receiverChargesAmount in ChargesInformation");
        ensureNotNull(receiverChargesCurrency,"receiverChargesCurrency in ChargesInformation");
        ensureArrayNotEmpty(senderCharges, "senderCharges in ChargesInformation");
        return new ChargesInformation(bearerCode, senderCharges, receiverChargesAmount, receiverChargesCurrency);
    }
}
