package edu.cmu.cs214.hw6;

import edu.cmu.cs214.hw6.dataPlugin.Data;
import edu.cmu.cs214.hw6.displayPlugin.BarChartPlugin;
import edu.cmu.cs214.hw6.framework.core.DisplayPlugin;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class BarChartTest {
    private BarChartPlugin barChartPlugin;

    @Before
    public void setUp() {
        barChartPlugin = new BarChartPlugin();
    }

    @Test
    public void testGetRetrievedData() {
        List<Data> input = new ArrayList<>();
        Data data1 = new Data("","2022-04-16", (float) 0.8);
        Data data2 = new Data("","2022-04-16", (float) 1);
        Data data3 = new Data("","2022-04-15", (float) 0.7);
        input.add(data1);
        input.add(data2);
        input.add(data3);
        DisplayPlugin bar = new BarChartPlugin();
        JSONObject output = bar.getVisualizedData(input);
        System.out.println(output);
    }
}
