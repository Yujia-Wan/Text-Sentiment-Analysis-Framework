package edu.cmu.cs214.hw6.framework.core;

import java.util.ArrayList;
import java.util.List;

public class FrameworkImpl implements Framework {
    private DataPlugin currentDataPlugin;
    private DisplayPlugin currentDisplayPlugin;
    private List<DataPlugin> registeredDataPlugins;
    private List<DisplayPlugin> registeredDisplayPlugins;

    public FrameworkImpl() {
        this.registeredDataPlugins = new ArrayList<>();
        this.registeredDisplayPlugins = new ArrayList<>();
    }

    /**
     * Registers a new {@link DataPlugin} with the framework.
     *
     * @param plugin Plugin to be registered.
     */
    public void registerDataPlugin(DataPlugin plugin) {
        plugin.onRegister(this);
        registeredDataPlugins.add(plugin);
    }

    /**
     * Registers a new {@link DisplayPlugin} with the framework.
     *
     * @param plugin Plugin to be registered.
     */
    public void registerDisplayPlugin(DisplayPlugin plugin) {
        plugin.onRegister(this);
        registeredDisplayPlugins.add(plugin);
    }
}
