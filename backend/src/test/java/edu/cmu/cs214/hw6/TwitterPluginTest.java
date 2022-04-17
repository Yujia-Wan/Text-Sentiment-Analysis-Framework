package edu.cmu.cs214.hw6;

import edu.cmu.cs214.hw6.dataPlugin.Data;
import edu.cmu.cs214.hw6.dataPlugin.TwitterPlugin;
import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

public class TwitterPluginTest {
    private DataPlugin dataPlugin;

    @Before
    public void setUp() {
        dataPlugin = new TwitterPlugin();
    }

    @Test
    public void testGetRetrievedData() {
        List<Data> result = dataPlugin.getRetrievedData("CarnegieMellon");
//        for (Data d: result) {
//            System.out.println(d.getText());
//        }
        assertTrue(result.size() > 0);
        assertEquals(10, result.size());
    }
}
