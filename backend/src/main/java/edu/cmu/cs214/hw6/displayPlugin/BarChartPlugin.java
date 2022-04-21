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
    private static final String DISPLAY_PLUGIN_NAME = "Bar Chart";
    private static final int NUMBER = 10;
    private Framework framework;

    @Override
    public String getDisplayPluginName() {
        return DISPLAY_PLUGIN_NAME;
    }

    @Override
    public JSONObject getVisualizedData(List<Data> data) {
        JSONObject json = new JSONObject();
        if (data.isEmpty()) {
            System.err.println("No data to plot!");
            return null;
        }
        
        Map<String, Integer> number = new HashMap<>();
        Map<String, Float> score = new HashMap<>();
        for (Data d : data) {
            d.setTime(d.getTime().substring(0, NUMBER));
            number.put(d.getTime(), number.getOrDefault(d.getTime(), 0) + 1);
            score.put(d.getTime(), score.getOrDefault(d.getTime(), (float)0.0) + d.getScore());
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
        return json;
    }

    @Override
    public void onRegister(Framework framework) {
        this.framework = framework;
    }
}
