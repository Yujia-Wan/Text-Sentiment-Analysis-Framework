package edu.cmu.cs214.hw6.framework.gui;

import edu.cmu.cs214.hw6.framework.core.FrameworkImpl;

public final class FrameworkState {
    private final String instruction;
    private final String data;

    private FrameworkState(String instruction, String data) {
        this.instruction = instruction;
        this.data = data;
    }

    public static FrameworkState forFramework(FrameworkImpl framework) {
        String instruction = framework.getInstruction();
        framework.setInstruction("");
        String data = framework.getResult();
        framework.resetResult("");
        return new FrameworkState(instruction, data);
    }

    @Override
    public String toString() {
        if (data.equals("")) {
            return ("{\"instruction\":\"" + this.instruction + "\"," +
                    " \"data\":\"\"}");
        }
        return ("{\"instruction\":\"" + this.instruction + "\"," +
                " \"data\":" + this.data + "}");
    }
}
