package com.envimate.examples.saga;

import static com.envimate.examples.saga.Action.action;

public class Main {
    public static void main(String[] args) {
        Saga.withAction(action(o -> {
            System.out.println("ACTION");
            return "some object";
        })).withCounterAction(action(o -> {
            System.out.println("COUNTER ACTION");
            return "some other object";
        })).execute("InputObject");


        Saga.withAction(action(o -> {
            System.out.println("problematic ACTION");
            throw new RuntimeException("oh");
        })).withCounterAction(action(o -> {
            System.out.println("COUNTER ACTION");
            return true;
        })).execute("InputObject");
    }
}
