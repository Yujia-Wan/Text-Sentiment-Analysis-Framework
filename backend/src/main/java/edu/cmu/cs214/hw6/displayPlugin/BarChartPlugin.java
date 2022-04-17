package edu.cmu.cs214.hw6.displayPlugin;

import edu.cmu.cs214.hw6.dataPlugin.Data;
import edu.cmu.cs214.hw6.framework.core.DisplayPlugin;
import edu.cmu.cs214.hw6.framework.core.Framework;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarChartPlugin implements DisplayPlugin {
    private static final String DISPLAY_PLUGIN_NAME = "bar";
    private Framework framework;

    @Override
    public String getDisplayPluginName() {
        return DISPLAY_PLUGIN_NAME;
    }

    @Override
    public JSONObject getRetrievedData(List<Data> list) {
        JSONObject json = new JSONObject();
        if (list.size() == 0) {
            json.put("errorMessage", "No data to plot!");
        }
        Map<String, Integer> number = new HashMap<>();
        Map<String, Float> score = new HashMap<>();
        for (Data data : list) {
            number.put(data.getTime(), number.getOrDefault(data.getTime(), 0) + 1);
            score.put(data.getTime(), score.getOrDefault(data.getTime(), (float)0.0) + data.getScore());
        }
        List<String> x = new ArrayList<>();
        List<Float> y = new ArrayList<>();
        for (String time : number.keySet()) {
            x.add(time);
            y.add(score.get(time)/number.get(time));
        }
        json.put("x", new JSONArray(x));
        json.put("y", new JSONArray(y));
        json.put("type", "bar");
        JSONArray array = new JSONArray();
        array.put(json);
        JSONObject output = new JSONObject();
        output.put("data", array);
        return output;
    }

    @Override
    public void onRegister(Framework framework) {
        this.framework = framework;
    }
}
