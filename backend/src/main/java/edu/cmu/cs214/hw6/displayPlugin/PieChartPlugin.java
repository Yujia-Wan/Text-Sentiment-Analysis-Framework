package edu.cmu.cs214.hw6.displayPlugin;

import edu.cmu.cs214.hw6.dataPlugin.Data;
import edu.cmu.cs214.hw6.framework.core.DisplayPlugin;
import edu.cmu.cs214.hw6.framework.core.Framework;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PieChartPlugin implements DisplayPlugin {
    private static final String DISPLAY_PLUGIN_NAME = "Pie Chart";
    private Framework framework;

    @Override
    public String getDisplayPluginName() {
        return DISPLAY_PLUGIN_NAME;
    }

    @Override
    public JSONObject getVisualizedData(List<Data> data) {
        if (data.isEmpty()) {
            System.err.println("No data to plot!");
            return null;
        }

        Integer positiveCount = 0;
        Integer neutralCount = 0;
        Integer negativeCount = 0;
        for (Data d: data) {
            if (d.getScore() > 0) {
                positiveCount++;
            } else if (d.getScore() == 0) {
                neutralCount++;
            } else  {
                negativeCount++;
            }
        }

        JSONObject json = new JSONObject();
        List<Integer> values = new ArrayList<>();
        values.add(positiveCount);
        values.add(neutralCount);
        values.add(negativeCount);
        json.put("values", new JSONArray(values));

        List<String> labels = new ArrayList<>();
        labels.add("Positive");
        labels.add("Neutral");
        labels.add("Negative");
        json.put("labels", new JSONArray(labels));

        json.put("type", "pie");
        return json;
    }

    @Override
    public void onRegister(Framework framework) {
        this.framework = framework;
    }
}
