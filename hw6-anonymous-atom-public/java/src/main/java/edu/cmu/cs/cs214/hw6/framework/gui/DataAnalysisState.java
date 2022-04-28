package edu.cmu.cs.cs214.hw6.framework.gui;
import edu.cmu.cs.cs214.hw6.framework.core.DataFrameworkImpl;
import edu.cmu.cs.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs.cs214.hw6.framework.core.VisPlugin;

import java.util.List;

/**
 * This class records all important states of the framework. Later, the Handlebar will extract these states and set the
 * template variables in resources/game_template.hbs.
 *
 * NOTICE:
 * If you want to add new template variables in game_template.hbs, please add them as instance variables here
 * and create their GETTER methods. Without their GETTER methods, the Handlebar will not recognize them.
 */
public final class DataAnalysisState {
    private List<String> registeredDataPlugins;
    private List<String> registeredVisPlugins;
    private List<String> addedQueries;
    private String json;
    private String dataPluginNotSelected;
    private String visPluginNotSelected;

    // GETTERS
    public List<String> getRegisteredDataPlugins() {
        return registeredDataPlugins;
    }

    public List<String> getRegisteredVisPlugins() {
        return registeredVisPlugins;
    }

    public List<String> getAddedQueries() {
        return addedQueries;
    }

    public String getJson() {
        return json;
    }

    public String getDataPluginNotSelected() {
        return dataPluginNotSelected;
    }

    public String getVisPluginNotSelected() {
        return visPluginNotSelected;
    }

    // CONSTRUCTOR
    private DataAnalysisState(List<String> registeredDataPlugins, List<String> registeredVisPlugins,
                              List<String> addedQueries, String json, String dataPluginNotSelected, String visPluginNotSelected) {
        this.registeredDataPlugins = registeredDataPlugins;
        this.registeredVisPlugins = registeredVisPlugins;
        this.addedQueries = addedQueries;
        this.json = json;
        this.dataPluginNotSelected = dataPluginNotSelected;
        this.visPluginNotSelected = visPluginNotSelected;
    }

    // Gets instance variables that will be rendered in the frontend from framework
    public static DataAnalysisState forDataAnalysis(DataFrameworkImpl dataFramework) {
        List<String> registeredDataPlugins = dataFramework.getRegisteredDataPlugins().stream().map(DataPlugin::toString).toList();
        List<String> registeredVisPlugins = dataFramework.getRegisteredVisPlugins().stream().map(VisPlugin::toString).toList();
        List<String> addedQueries = dataFramework.getAddedQueries();
        String json = dataFramework.getChartInfo();

        // If user attempts to run the analysis without selecting a data plugin, notify them by rendering this sentence
        String dataPluginNotSelected = (dataFramework.isWithoutSelectingDataPlugin()) ? "You forgot to select " +
                "a data plugin before adding your search query!" : "";

        // If user attempts to visualize the results without selecting a visualization plugin, notify them by rendering this sentence
        String visPluginNotSelected = (dataFramework.isWithoutSelectingVisPlugin()) ? "You forgot to select a visualization plugin!" : "";

        return new DataAnalysisState(registeredDataPlugins, registeredVisPlugins, addedQueries, json, dataPluginNotSelected, visPluginNotSelected);
    }
}
