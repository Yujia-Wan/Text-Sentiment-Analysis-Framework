package edu.cmu.cs.cs214.hw6.framework.core;

import com.alibaba.fastjson.JSONObject;
import edu.cmu.cs.cs214.hw6.framework.google.GoogleAPI;
import edu.cmu.cs.cs214.hw6.framework.utils.DataNode;

import edu.cmu.cs.cs214.hw6.framework.utils.ResultNode;
import org.apache.commons.collections.ListUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class DataFrameworkImplTest {

    DataFrameworkImpl dataFramework;

    @Before
    public void setUp() {
        dataFramework = new DataFrameworkImpl();
    }

    @Test
    public void extractData() throws GeneralSecurityException, IOException {
        DataPlugin dataPlugin = mock(DataPlugin.class);
        doNothing().when(dataPlugin).connectToSource();
        doNothing().when(dataPlugin).extractData(isA(String.class));
        List<DataNode> listOdDataNodes = new ArrayList<>();
        DataNode dataNode = new DataNode("democracy", "");
        listOdDataNodes.add(dataNode);
        when(dataPlugin.processData()).thenReturn(listOdDataNodes);

        dataFramework.setCurrentDataPlugin(dataPlugin);
        dataFramework.extractData("america");
        List<String> topics = new ArrayList<>();
        topics.add("america");

        assertTrue(ListUtils.isEqualList(dataFramework.getDataNodes(), listOdDataNodes));
        assertTrue(ListUtils.isEqualList(dataFramework.getAddedQueries(), topics));
    }

    @Test
    public void analyzeEntitySentiment() throws Exception {
        MockedStatic<GoogleAPI> theMock = Mockito.mockStatic(GoogleAPI.class);

        JSONObject mockSentiment = new JSONObject();
        mockSentiment.put("magnitude", 0.8F);
        mockSentiment.put("score", 0.5F);
        mockSentiment.put("salience", 1.0F);

        JSONObject mockEntitySentiment = new JSONObject();
        mockEntitySentiment.put("magnitude", 0.7F);
        mockEntitySentiment.put("score", -0.5F);
        mockEntitySentiment.put("salience", 0.6F);
        mockEntitySentiment.put("entity", "testEntity");

        theMock.when(() -> GoogleAPI.analyzeSentimentText(isA(String.class))).thenReturn(mockSentiment);
        theMock.when(() -> GoogleAPI.entitySentimentText(isA(String.class))).thenReturn(mockEntitySentiment);

        List<DataNode> listOfDataNodes = new ArrayList<>();
        listOfDataNodes.add(new DataNode(null, ""));
        listOfDataNodes.add(new DataNode("test", ""));

        dataFramework.setDataNodes(listOfDataNodes);
        dataFramework.analyzeEntitySentiment();

        List<ResultNode> listOfResultNodes = new ArrayList<>();
        listOfResultNodes.add(new ResultNode("testEntity", 0.7F, -0.5F, 0.6F));
        listOfResultNodes.add(new ResultNode("test", 0.8F, 0.5F, 1.0F));

        assertTrue(ListUtils.isEqualList(dataFramework.getResultNodes(), listOfResultNodes));
    }

    @Test
    public void visualize() {
        VisPlugin visPlugin = mock(VisPlugin.class);

        when(visPlugin.processData(any())).thenReturn(null);
        when(visPlugin.generateChartInfo(any())).thenReturn("chartInfo");

        dataFramework.setCurrentVisPlugin(visPlugin);
        dataFramework.visualize();

        assertEquals("chartInfo", dataFramework.getChartInfo());
    }
}