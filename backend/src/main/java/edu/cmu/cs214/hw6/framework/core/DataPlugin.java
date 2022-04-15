package edu.cmu.cs214.hw6.framework.core;
import edu.cmu.cs214.hw6.dataPlugin.Data;
import org.json.JSONObject;

import java.util.List;

/**
 * The data plug-in interface that plug-ins use to implement and register data
 * source with the {@link Framework}.
 */
public interface DataPlugin {

    List<Data> getDataSource(JSONObject json);

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
