package edu.cmu.cs214.hw6;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.DisplayPlugin;
import edu.cmu.cs214.hw6.framework.core.FrameworkImpl;
import edu.cmu.cs214.hw6.framework.gui.FrameworkState;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private static final int PORT = 8000;
    private FrameworkImpl framework;
    private List<DataPlugin> dataPlugins;
    private List<DisplayPlugin> displayPlugins;

    public App() throws IOException {
        super(PORT);

        this.framework = new FrameworkImpl();
        this.dataPlugins = loadDataPlugins();
        for (DataPlugin p: dataPlugins) {
            framework.registerDataPlugin(p);
        }
        this.displayPlugins = loadDisplayPlugins();
        for (DisplayPlugin p: displayPlugins) {
            framework.registerDisplayPlugin(p);
        }

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("Running!");
    }

    @Override
    public  Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        if (uri.equals("/submit")) {
            DataPlugin dataPlugin = dataPlugins.get(Integer.parseInt(params.get("dataplugin")));
            String dataPluginIndex = params.get("datapluginindex");
            DisplayPlugin displayPlugin = displayPlugins.get(Integer.parseInt(params.get("displayplugin")));
            framework.process(dataPlugin, dataPluginIndex, displayPlugin);
        }

        FrameworkState frameworkState = FrameworkState.forFramework(framework);
        return newFixedLengthResponse(frameworkState.toString());
    }

    /**
     * Load data plugins listed in META-INF/services/...
     *
     * @return List of instantiated data plugins
     */
    private static List<DataPlugin> loadDataPlugins() {
        ServiceLoader<DataPlugin> plugins = ServiceLoader.load(DataPlugin.class);
        List<DataPlugin> result = new ArrayList<>();
        for (DataPlugin p: plugins) {
            System.out.println("Loaded data plugin " + p.getDataPluginName());
            result.add(p);
        }
        return result;
    }

    /**
     * Load display plugins listed in META-INF/services/...
     *
     * @return List of instantiated display plugins
     */
    private static List<DisplayPlugin> loadDisplayPlugins() {
        ServiceLoader<DisplayPlugin> plugins = ServiceLoader.load(DisplayPlugin.class);
        List<DisplayPlugin> result = new ArrayList<>();
        for (DisplayPlugin p: plugins) {
            System.out.println("Loaded display plugin " + p.getDisplayPluginName());
            result.add(p);
        }
        return result;
    }
}
