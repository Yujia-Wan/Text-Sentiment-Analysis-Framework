package edu.cmu.cs214.hw6.framework.core;

import edu.cmu.cs214.hw6.dataPlugin.Data;

import java.util.List;

public interface Framework {

    /**
     * Analyzes text message sentiment using Google's Natural Language API.
     *
     * @param data A list of texts data to be analyzed.
     * @return Sentiment analysis result.
     */
    List<Data> analyzeTextSentiment(List<Data> data);

    /**
     * The whole data process of this framework:
     * (1) Use data plugin api to get retrieved data.
     * (2) Perform sentiment analysis.
     * (3) Use display plugin to get visualized data.
     *
     * @param dataPlugin Data plugin.
     * @param dataPluginIndex Keyword for data plugin to retrieve.
     * @param displayPlugin Display plugin.
     */
    void process(DataPlugin dataPlugin, String dataPluginIndex, DisplayPlugin displayPlugin);
}
