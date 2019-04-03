package com.envimate.examples.saga;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Action {
    final Function actionFunction;

    public static Action action(final Function actionFunction) {
        return new Action(actionFunction);
    }
}
