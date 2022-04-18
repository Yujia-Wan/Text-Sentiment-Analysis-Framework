package edu.cmu.cs214.hw6.framework.gui;

import edu.cmu.cs214.hw6.framework.core.FrameworkImpl;

public class DataState {
    private final String data;


    public DataState(String data) {
        this.data = data;
    }

    public static DataState forState(FrameworkImpl frameworkImpl) {
        String data = frameworkImpl.getResult();
        System.out.println(data);
        return new DataState(data);
    }

    @Override
    public String toString() {
        return "{ \"data\": "  + this.data +  "}";
    }
}
