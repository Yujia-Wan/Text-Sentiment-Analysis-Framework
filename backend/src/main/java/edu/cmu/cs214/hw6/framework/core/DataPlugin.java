package edu.cmu.cs214.hw6.framework.core;

import edu.cmu.cs214.hw6.dataPlugin.Data;
import java.util.List;

/**
 * The data plug-in interface that plug-ins use to implement and register data
 * source with the {@link Framework}.
 */
public interface DataPlugin {

    /**
     * Gets the name of the plug-in data source.
     *
     * @return Name of data plugin.
     */
    String getDataPluginName();

    /**
     * Retrieves texts and time from given data source. Records text fragment and corresponding time in a list of
     * Data objects. The score field will be set to 0 as a default value.
     *
     * @param index Index used for search. Input username for Twitter (like "CarnegieMellon"). Input part of video
     *              url for YouTube (For example, if url is https://www.youtube.com/watch?v=Hp_Eg8NMfT0, you should
     *              input "Hp_Eg8NMfT0").
     * @return A list of data with text and time has been set (Refer to Data.java for more details).
     */
    List<Data> getRetrievedData(String index);

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
