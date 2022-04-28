package edu.cmu.cs.cs214.hw6.framework.core;
import com.alibaba.fastjson.JSONObject;
import edu.cmu.cs.cs214.hw6.framework.google.GoogleAPI;
import edu.cmu.cs.cs214.hw6.framework.utils.DataNode;
import edu.cmu.cs.cs214.hw6.framework.utils.ResultNode;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Please refer to the documentation in the DataFramework interface, where there are enough information
 * for you to have a general understanding of the framework for implementing more plugins
 */
public class DataFrameworkImpl implements DataFramework {
    private List<DataPlugin> registeredDataPlugins;
    private List<VisPlugin> registeredVisPlugins;
    private DataPlugin currentDataPlugin;
    private VisPlugin currentVisPlugin;

    private List<DataNode> dataNodes;
    private List<ResultNode> resultNodes;
    private String chartInfo;

    private List<String> addedQueries; // Added queries
    private boolean isConnectedToSource; // true if the framework has connected to the API of the current data plugin
    private boolean withoutSelectingDataPlugin; // true if no data plugin was selected
    private boolean withoutSelectingVisPlugin; // true if no visualization plugin was selected

    public DataFrameworkImpl() {
        registeredDataPlugins = new ArrayList<>();
        registeredVisPlugins = new ArrayList<>();
        dataNodes = new ArrayList<>();
        resultNodes = new ArrayList<>();
        addedQueries = new ArrayList<>();
        isConnectedToSource = false;
        withoutSelectingDataPlugin = false;
        withoutSelectingVisPlugin = false;
    }

    /**
     * Extracts and saves data related to {@code query} (a Twitter user ID, for example) from {@code currentDataPlugin}.
     * The data is a list of {@link edu.cmu.cs.cs214.hw6.framework.utils.DataNode}.
     *
     * @param query search query for {@code currentDataPlugin}
     * @throws GeneralSecurityException security-related exceptions
     * @throws IOException
     */
    @Override
    public void extractData(String query) throws GeneralSecurityException, IOException {
        chartInfo = "";
        withoutSelectingDataPlugin = false;
        withoutSelectingVisPlugin = false;

        // Connect to the API used in the current data plugin if not connected
        if (!isConnectedToSource) {
            currentDataPlugin.connectToSource();
            isConnectedToSource = true;
        }

        currentDataPlugin.extractData(query);
        dataNodes.addAll(currentDataPlugin.processData());

        // Add the query to addedQueries, which will be rendered in the frontend
        addedQueries.add(query);
    }

    /**
     * Analyzes the entities(if not presented in the data) and sentiments of the extracted data, {@code dataNodes},
     * using Google's NL API. The analysis results are then saved in {@code resultNodes}, a list of
     * {@link edu.cmu.cs.cs214.hw6.framework.utils.ResultNode}
     *
     * If a {@link edu.cmu.cs.cs214.hw6.framework.utils.DataNode}'s {@code entity} field is null, the API will figure out what
     * the entity is for you. If {@code entity} is not null, the API will not.
     * Other three things determined by the Google's Natural Language API: magnitude, score, salience. Read about
     * their meanings at https://cloud.google.com/natural-language/docs/basics
     */
    @Override
    public void analyzeEntitySentiment() {
        // Clear all result nodes first
        resultNodes.clear();

        if (dataNodes.size() == 0) {
            return;
        }

        try {
            for (DataNode dn : dataNodes) {
                // If no entity was not previously set by the data plugin, Google API determines the entity.
                if (dn.getEntity() == null) {
                    JSONObject res = GoogleAPI.entitySentimentText(dn.getText());
                    if (res != null) {
                        ResultNode rn = new ResultNode((String) res.get("entity"), (float) res.get("magnitude"),
                                (float) res.get("score"), (float) res.get("salience"));
                        resultNodes.add(rn);
                    }
                // if an entity was set by the data plugin, use the one provided.
                } else {
                    JSONObject res = GoogleAPI.analyzeSentimentText(dn.getText());
                    if (res != null) {
                        ResultNode rn = new ResultNode(dn.getEntity(), (float) res.get("magnitude"),
                                (float) res.get("score"), (float) res.get("salience"));
                        resultNodes.add(rn);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a string in Json, {@code chartInfo}, a JSON Representation of ECharts Option Object created using
     * ECharts-Java library.
     *
     * This JSON string will be transferred to the frontend through {@link edu.cmu.cs.cs214.hw6.framework.gui.DataAnalysisState} later.
     */
    @Override
    public void visualize() {
        // gui related
        withoutSelectingVisPlugin = false;

        // Get a list of result nodes produced by the visualization plugin
        List<ResultNode> processedResults = currentVisPlugin.processData(resultNodes);

        // Generate a json string that will be used by frontend javascript
        chartInfo = currentVisPlugin.generateChartInfo(processedResults);
    }

    public void registerDataPlugin(DataPlugin dataPlugin) {
        registeredDataPlugins.add(dataPlugin);
    }

    public void registerVisPlugin(VisPlugin visPlugin) {
        registeredVisPlugins.add(visPlugin);
    }

    // Reset the class
    public void clearExtractedData() {
        dataNodes.clear();
        addedQueries.clear();
        resultNodes.clear();
        chartInfo = "";
        withoutSelectingDataPlugin = false;
        withoutSelectingVisPlugin = false;
    }

    public void setCurrentDataPlugin(DataPlugin plugin) {
        this.currentDataPlugin = plugin;
        isConnectedToSource = false;
    }

    public void setCurrentVisPlugin(VisPlugin plugin) {
        this.currentVisPlugin = plugin;
    }

    public String getCurrentVisPlugin() {
        return currentVisPlugin.toString();
    }

    public String getCurrentDataPlugin() {
        return currentDataPlugin.toString();
    }

    public DataPlugin getCurrentDataPluginObject() {
        return currentDataPlugin;
    }

    public VisPlugin getCurrentVisPluginObject() {
        return currentVisPlugin;
    }

    public List<String> getAddedQueries() {
        return addedQueries;
    }

    public String getChartInfo() {
        return chartInfo;
    }

    public List<DataNode> getDataNodes() {
        return dataNodes;
    }

    public List<ResultNode> getResultNodes() {
        return resultNodes;
    }

    public void setDataNodes(List<DataNode> dataNodes) {
        this.dataNodes = dataNodes;
    }

    public boolean isWithoutSelectingDataPlugin() {
        return withoutSelectingDataPlugin;
    }

    public boolean isWithoutSelectingVisPlugin() {
        return withoutSelectingVisPlugin;
    }

    public void setWithoutSelectingDataPlugin(boolean withoutSelectingDataPlugin) {
        this.withoutSelectingDataPlugin = withoutSelectingDataPlugin;
    }

    public void setWithoutSelectingVisPlugin(boolean withoutSelectingVisPlugin) {
        this.withoutSelectingVisPlugin = withoutSelectingVisPlugin;
    }

    public List<DataPlugin> getRegisteredDataPlugins() {
        return registeredDataPlugins;
    }

    public List<VisPlugin> getRegisteredVisPlugins() {
        return registeredVisPlugins;
    }
}