package edu.cmu.cs214.hw6;

import edu.cmu.cs214.hw6.dataPlugin.Data;
import edu.cmu.cs214.hw6.dataPlugin.YouTubePlugin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.List;

public class YouTubePluginTest {
    private YouTubePlugin youTubePlugin;

    @Before
    public void setUp() {
        youTubePlugin = new YouTubePlugin();
    }

    @Test
    public void testGetRetrievedData() {
        List<Data> list = youTubePlugin.getRetrievedData("E-T1gEeUqwA");
        System.out.println(list);
        assertTrue(list.size() > 0);
    }
}
