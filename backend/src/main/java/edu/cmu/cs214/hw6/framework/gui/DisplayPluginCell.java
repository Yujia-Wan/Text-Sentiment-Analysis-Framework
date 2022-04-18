package edu.cmu.cs214.hw6.framework.gui;

public class DisplayPluginCell {
    private final String name;
    private final String link;

    public DisplayPluginCell(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "{ \"name\": \"" + this.name + "\"," +
                " \"link\": \"" + this.link + "\"}";
    }
}
