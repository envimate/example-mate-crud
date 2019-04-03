package com.envimate.examples.saga;

public class Saga {
    private final Action action;
    private Action counterAction;

    public Saga(final Action action) {
        this.action = action;
    }

    public static Saga withAction(final Action action) {
        return new Saga(action);
    }

    public  Saga withCounterAction(final Action counterAction) {
        this.counterAction = counterAction;
        return this;
    }

    public void execute(final String inputObject) {
        try {
            this.action.actionFunction.apply(inputObject);
        } catch(final Exception e) {
            this.counterAction.actionFunction.apply(inputObject);
        }
    }
}
