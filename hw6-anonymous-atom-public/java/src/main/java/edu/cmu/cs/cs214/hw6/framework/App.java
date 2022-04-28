package edu.cmu.cs.cs214.hw6.framework;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import edu.cmu.cs.cs214.hw6.framework.core.DataFrameworkImpl;
import edu.cmu.cs.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs.cs214.hw6.framework.core.VisPlugin;
import edu.cmu.cs.cs214.hw6.framework.gui.DataAnalysisState;
import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Template template;
    private DataFrameworkImpl dataAnalysisBoard;
    private List<DataPlugin> dataPlugins;
    private List<VisPlugin> visPlugins;

    public App() throws IOException {
        super(8080);

        this.dataAnalysisBoard = new DataFrameworkImpl();
        dataPlugins = loadDataPlugins();
        for (DataPlugin dp: dataPlugins){
            dataAnalysisBoard.registerDataPlugin(dp);
        }
        visPlugins = loadVisPlugins();
        for (VisPlugin vp: visPlugins){
            dataAnalysisBoard.registerVisPlugin(vp);
        }

        Handlebars handlebars = new Handlebars();
        this.template = handlebars.compile("game_template");
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();

        if (session.getMethod() == Method.POST && uri.equals("/data-plugin")) {
            try {
                session.parseBody(new HashMap<>());
                Map<String, String> params = session.getParms();
                if (params.get("search_button").equals("add")) {
                    String dataPlugin = params.get("data_plugin");
                    if (getSelectedDataPlugin(dataPlugin) != null) {
                        String search_input = params.get("search_input");
                        dataAnalysisBoard.setCurrentDataPlugin(getSelectedDataPlugin(dataPlugin));
                        dataAnalysisBoard.extractData(search_input);
                        dataAnalysisBoard.analyzeEntitySentiment();
                    } else {
                        dataAnalysisBoard.setWithoutSelectingDataPlugin(true);
                    }
                } else if (params.get("search_button").equals("clear")) { // clear topics/IDs
                    dataAnalysisBoard.clearExtractedData();
                }
            } catch (IOException | ResponseException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        }

        else if (session.getMethod() == Method.POST && uri.equals("/vis-plugin")) {
            try {
                session.parseBody(new HashMap<>());
                Map<String, String> params = session.getParms();
                String visPlugin = params.get("vis_plugin");
                if (getSelectedVisPlugin(visPlugin) != null) {
                    dataAnalysisBoard.setCurrentVisPlugin(getSelectedVisPlugin(visPlugin));
                    dataAnalysisBoard.visualize();
                } else {
                    dataAnalysisBoard.setWithoutSelectingVisPlugin(true);
                }
            } catch (IOException | ResponseException e) {
                e.printStackTrace();
            }
        }

        DataAnalysisState dataAnalysisState = DataAnalysisState.forDataAnalysis(dataAnalysisBoard);
        String HTML = null;
        try {
            HTML = this.template.apply(dataAnalysisState);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFixedLengthResponse(HTML);

    }

    /**
     * Load data plugins listed in META-INF/services/...
     *
     * @return List of instantiated data plugins
     */
    private static List<DataPlugin> loadDataPlugins() {
        ServiceLoader<DataPlugin> plugins = ServiceLoader.load(DataPlugin.class);
        List<DataPlugin> result = new ArrayList<>();
        for (DataPlugin plugin : plugins) {
            System.out.println("Loaded plugin " + plugin.toString());
            result.add(plugin);
        }
        return result;
    }

    /**
     * Load visualization plugins listed in META-INF/services/...
     *
     * @return List of instantiated data plugins
     */
    private static List<VisPlugin> loadVisPlugins() {
        ServiceLoader<VisPlugin> plugins = ServiceLoader.load(VisPlugin.class);
        List<VisPlugin> result = new ArrayList<>();
        for (VisPlugin plugin : plugins) {
            System.out.println("Loaded plugin " + plugin.toString());
            result.add(plugin);
        }
        return result;
    }

    private DataPlugin getSelectedDataPlugin(String name) {
        for (DataPlugin p : dataPlugins) {
            if (p.toString().equals(name)) {
                return p;
            }
        }
        return null;
    }

    private VisPlugin getSelectedVisPlugin(String name) {
        for (VisPlugin p : visPlugins) {
            if (p.toString().equals(name)) {
                return p;
            }
        }
        return null;
    }
}

