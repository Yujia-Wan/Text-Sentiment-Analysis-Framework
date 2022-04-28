package edu.cmu.cs.cs214.hw6.framework.core;

import edu.cmu.cs.cs214.hw6.framework.utils.DataNode;
import edu.cmu.cs.cs214.hw6.framework.utils.ResultNode;

import java.util.HashMap;
import java.util.List;

public interface VisPlugin {
    /**
     * Processes the results of the sentimental analysis before visualizing them.
     * For example, sort the results based on the sentiment score.
     * @param analysisResult the results from the sentimental analysis
     * @return the processed results
     */
    List<ResultNode> processData(List<ResultNode> analysisResult);

    /**
     * Sets the visualization option here like the type of the chart as well as the data to be visualized in
     * correct format. The above settings will be rendered into JSON Representation of ECharts Option Object
     * using ECharts-Java library. And this JSON string will be sent to the frontend and transferred into HTML
     * using ECharts library.
     *
     * Notice that the user has to use ECharts-Java to generate the JSON string because the frontend use EChart
     * library accordingly to transfer the JSON string into HTML to be visualized
     *
     * @param analysisRes the processed result from the sentimental analysis
     * @return A JSON Representation of ECharts Option Object
     */
    String generateChartInfo(List<ResultNode> analysisRes);

    String toString();
}
