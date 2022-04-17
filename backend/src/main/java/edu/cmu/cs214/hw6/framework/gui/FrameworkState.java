package edu.cmu.cs214.hw6.framework.gui;

import edu.cmu.cs214.hw6.framework.core.FrameworkImpl;

import java.util.Arrays;
import java.util.List;

public class FrameworkState {
    private DataPluginCell[] dataPlugins;
    private DisplayPluginCell[] displayPlugins;
    private String currentDataPlugin;
    private String dataPluginIndex;
    private String currentDisplayPlugin;

    private FrameworkState(DataPluginCell[] dataPlugins, DisplayPluginCell[] displayPlugins, String currentDataPlugin, String dataPluginIndex, String currentDisplayPlugin) {
        this.dataPlugins = dataPlugins;
        this.displayPlugins = displayPlugins;
        this.currentDataPlugin = currentDataPlugin;
        this.dataPluginIndex = dataPluginIndex;
        this.currentDisplayPlugin = currentDisplayPlugin;
    }

    public static FrameworkState forFramework(FrameworkImpl framework) {
        DataPluginCell[] dataPluginCells = getDataPluginCells(framework);
        DisplayPluginCell[] displayPluginCells = getDisplayPluginCells(framework);
        String currentDataPlugin = framework.getCurrentDataPlugin();
        String dataPluginIndex = framework.getDataPluginIndex();
        String currentDisplayPlugin = framework.getCurrentDisplayPlugin();
        return new FrameworkState(dataPluginCells, displayPluginCells, currentDataPlugin, dataPluginIndex, currentDisplayPlugin);
    }

    private static DataPluginCell[] getDataPluginCells(FrameworkImpl framework) {
        List<String> pluginName = framework.getRegisteredDataPluginName();
        DataPluginCell[] cells = new DataPluginCell[pluginName.size()];
        for (int i = 0; i < pluginName.size(); i++) {
            String link = "/datapluginname?x=" + i;
            cells[i] = new DataPluginCell(pluginName.get(i), link);
        }
        return cells;
    }

    public static DisplayPluginCell[] getDisplayPluginCells(FrameworkImpl framework) {
        List<String> pluginName = framework.getRegisteredDisplayPluginName();
        DisplayPluginCell[] cells = new DisplayPluginCell[pluginName.size()];
        for (int i = 0; i < pluginName.size(); i++) {
            String link = "/displaypluginname?z=" + i;
            cells[i] = new DisplayPluginCell(pluginName.get(i), link);
        }
        return cells;
    }

    @Override
    public String toString() {
        return (" \"dataPlugins\": " + Arrays.toString(dataPlugins) + "," +
                " \"displayPlugins\": " + Arrays.toString(displayPlugins) + "," +
                " \"currentDataPluginName\": \"" + this.currentDataPlugin + "\"," +
                " \"dataPluginIndex\": \"" + this.dataPluginIndex + "\"," +
                " \"currentDisplayPluginName\": \"" + this.currentDisplayPlugin + "\",").replace(null, "");
    }
}
