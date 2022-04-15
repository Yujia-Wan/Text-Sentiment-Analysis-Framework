package edu.cmu.cs214.hw6.framework.core;

/**
 * The display plug-in interface that plug-ins use to implement and register visualization
 * with the {@link Framework}.
 */
public interface DisplayPlugin {

    /**
     * Gets the name of the plug-in visualization.
     */
    String getDisplayName();

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
