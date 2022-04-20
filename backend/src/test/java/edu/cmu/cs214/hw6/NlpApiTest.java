package edu.cmu.cs214.hw6;

import edu.cmu.cs214.hw6.dataPlugin.Data;
import edu.cmu.cs214.hw6.framework.core.Framework;
import edu.cmu.cs214.hw6.framework.core.FrameworkImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class NlpApiTest {
    private Framework framework;

    @Before
    public void setUp() {
        framework = new FrameworkImpl();
    }

    @Test
    public void testAnalyzeSentimentText() {
        String[] texts = {"I love this!", "I hate this!"};
        List<Data> data = new ArrayList<>();
        for (String text: texts) {
            Data d = new Data(text, null, 0);
            data.add(d);
        }
        data = framework.analyzeTextSentiment(data);
        assertTrue(data.get(0).getScore() > 0);
        assertTrue(data.get(1).getScore() < 0);
    }
}
