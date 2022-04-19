package edu.cmu.cs214.hw6.framework.core;

import edu.cmu.cs214.hw6.dataPlugin.Data;

import java.util.List;

/**
 * The framework interface that performs sentiment analysis.
 */
public interface Framework {

    /**
     * Analyzes text message sentiment using Google's Natural Language API.
     * Input: A list of data retrieved from data source in data plugin that has text and time fields recorded.
     * Output: A list of data with score of every text added.
     * (Every Data object has three fields: text, time and score. Text is a text fragment of data source, for example,
     * a tweet of a Twitter user, or a comment of a YouTube video. Time is the text's creation time. Score is a float
     * that will be recorded after this framework performs sentiment analysis on the text. Refer to Data.java for more
     * details.)
     *
     * @param data A list of texts data to be analyzed.
     * @return Sentiment analysis result.
     */
    List<Data> analyzeTextSentiment(List<Data> data);

    /**
     * The whole data processing of this framework:
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
