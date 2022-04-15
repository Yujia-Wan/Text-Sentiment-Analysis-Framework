package edu.cmu.cs214.hw6;

import edu.cmu.cs214.hw6.dataPlugin.Data;
import edu.cmu.cs214.hw6.dataPlugin.YouTubePlugin;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.json.JSONObject;
import java.util.List;

public class YouTubeDataTest {

    @Test
    public void YouTubeDataTest() {
        JSONObject json = new JSONObject();
        json.put("dataSourceURL", "E-T1gEeUqwA");
        YouTubePlugin youTubePlugin = new YouTubePlugin();
        List<Data> list = youTubePlugin.getDataSource(json);
        System.out.println(list);
        assertTrue(list.size() > 0);
    }
}
