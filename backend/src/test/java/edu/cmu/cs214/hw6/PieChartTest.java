package edu.cmu.cs214.hw6;

import edu.cmu.cs214.hw6.dataPlugin.Data;
import edu.cmu.cs214.hw6.displayPlugin.PieChartPlugin;
import edu.cmu.cs214.hw6.framework.core.DisplayPlugin;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class PieChartTest {
    private DisplayPlugin plugin;

    @Before
    public void setUp() {
        plugin = new PieChartPlugin();
    }

    @Test
    public void testGetVisualizedData() {
        List<Data> input = new ArrayList<>();
        Data data1 = new Data("","", (float) 0.8);
        Data data2 = new Data("","", (float) -0.6);
        Data data3 = new Data("","", (float) 0);
        Data data4 = new Data("","", (float) -0.3);
        Data data5 = new Data("","", (float) 0.7);
        input.add(data1);
        input.add(data2);
        input.add(data3);
        input.add(data4);
        input.add(data5);
        JSONObject output = plugin.getVisualizedData(input);
        System.out.println(output);
    }
}
