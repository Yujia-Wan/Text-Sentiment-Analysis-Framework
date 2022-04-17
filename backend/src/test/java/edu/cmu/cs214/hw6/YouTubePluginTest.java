package edu.cmu.cs214.hw6;

import edu.cmu.cs214.hw6.dataPlugin.Data;
import edu.cmu.cs214.hw6.dataPlugin.YouTubePlugin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.List;
import org.json.JSONObject;

public class YouTubePluginTest {
    private YouTubePlugin youTubePlugin;

    @Before
    public void setUp() {
        youTubePlugin = new YouTubePlugin();
    }

    @Test
    public void testGetRetrievedData() {
        JSONObject json = new JSONObject();
        json.put("dataSourceURL", "E-T1gEeUqwA");
        List<Data> list = youTubePlugin.getRetrievedData(json);
//        System.out.println(list);
        assertTrue(list.size() > 0);
    }
}
