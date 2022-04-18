package edu.cmu.cs214.hw6.framework.gui;

import edu.cmu.cs214.hw6.framework.core.FrameworkImpl;

public class State {
    private final String instruction;


    public State(String instruction) {
        this.instruction = instruction;
    }

    public static State forState(FrameworkImpl frameworkImpl) {
        String instruction = frameworkImpl.getResult();
        System.out.println(instruction);
        return new State(instruction);
    }

    @Override
    public String toString() {
        return "{ \"instruction\": "  + this.instruction +  "}";
    }
}
