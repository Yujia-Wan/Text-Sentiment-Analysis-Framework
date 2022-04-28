package edu.cmu.cs.cs214.hw6.framework.plugin.visualization;
import edu.cmu.cs.cs214.hw6.framework.core.VisPlugin;
import edu.cmu.cs.cs214.hw6.framework.utils.ResultNode;
import org.icepear.echarts.Bar;
import org.icepear.echarts.charts.bar.BarSeries;
import org.icepear.echarts.render.Engine;
import java.util.Comparator;
import java.util.List;


public class BarVisPlugin implements VisPlugin {
    @Override
    public String toString() {
        return "Bar";
    }

    /**
     * Sort the results based on the sentimental magnitude
     * @param analysisRes the initial results from the sentimental analysis
     * @return The processed results from the sentimental analysis
     */
    @Override
    public List<ResultNode> processData(List<ResultNode> analysisRes) {
        analysisRes.sort(new Comparator<ResultNode>() {
            // Sort result nodes based on magnitude
            @Override
            public int compare(ResultNode o1, ResultNode o2) {
                return Float.compare(o1.getMagnitude(), o2.getMagnitude());
            }
        });
        return analysisRes;
    }

    /**
     * Generate ECharts Option Object and its JSON Representation using ECharts-Java library
     * @param analysisResults the processed results from the sentimental analysis
     * @return the JSON representation for the ECharts Option Object
     */
    @Override
    public String generateChartInfo(List<ResultNode> analysisResults) {
        String[] entities = new String[analysisResults.size()];
        Number[] salience = new Number[analysisResults.size()];
        Number[] score = new Number[analysisResults.size()];
        Number[] magnitude = new Number[analysisResults.size()];

        int numOfNodes = analysisResults.size();

        // packs entities, salience, score, and magnitude into lists
        for (int i = 0; i < numOfNodes; i++) {
            entities[i] = analysisResults.get(i).getEntity();
            salience[i] = analysisResults.get(i).getSalience();
            score[i] = analysisResults.get(i).getScore();
            magnitude[i] = analysisResults.get(i).getMagnitude();
        }

        // Create bar chart options
        Bar bar = new Bar()
                .setLegend()
                .setTooltip("item")
                .addXAxis(entities)
                .addYAxis("salience and score")
                .addYAxis("magnitude")
                .addSeries("salience", salience)
                .addSeries("score", score)
                .addSeries(new BarSeries()
                .setData(magnitude).setName("magnitude").setYAxisIndex(1).setType("line"));

        Engine engine = new Engine();
        return engine.renderJsonOption(bar);
    }
}
