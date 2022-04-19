package edu.cmu.cs214.hw6.framework.core;

import edu.cmu.cs214.hw6.dataPlugin.Data;
import org.json.JSONObject;

import java.util.List;

/**
 * The display plug-in interface that plug-ins use to implement and register visualization
 * with the {@link Framework}.
 */
public interface DisplayPlugin {

    /**
     * Gets the name of the plug-in visualization.
     *
     * @return Name of diaplay plugin.
     */
    String getDisplayPluginName();

    /**
     * Calculates display data and formats it as an JSONObject.
     * For example, for bar chart, the format is:
     * {"x":["2022-04-15","2022-04-16"],"y":[0.7,0.9],"type":"bar"}
     * For pie chart, the format is:
     * {"values":[2,1,2],"labels":["Positive","Neutral","Negative"],"type":"pie"}
     *
     * @param data A list of data.
     * @return JSONObject sent to frontend.
     */
    JSONObject getVisualizedData(List<Data> data);

    /**
     * Called (only once) when the plug-in is first registered with the
     * framework, giving the plug-in a chance to perform any initial set-up
     * before the sentiment analysis has begun (if necessary).
     *
     * @param framework The {@link Framework} instance with which the plug-in
     *                  was registered.
     */
    void onRegister(Framework framework);
}
