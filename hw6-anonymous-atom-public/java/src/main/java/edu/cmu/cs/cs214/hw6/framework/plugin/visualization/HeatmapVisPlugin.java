package edu.cmu.cs.cs214.hw6.framework.plugin.visualization;

import edu.cmu.cs.cs214.hw6.framework.core.VisPlugin;
import edu.cmu.cs.cs214.hw6.framework.utils.ResultNode;
import org.icepear.echarts.Heatmap;
import org.icepear.echarts.charts.heatmap.HeatmapSeries;
import org.icepear.echarts.components.series.SeriesLabel;
import org.icepear.echarts.render.Engine;

import java.util.ArrayList;
import java.util.List;

public class HeatmapVisPlugin implements VisPlugin {

    @Override
    public String toString() {
        return "Heatmap";
    }

    @Override
    public List<ResultNode> processData(List<ResultNode> analysisResult) {
        return analysisResult;
    }

    @Override
    public String generateChartInfo(List<ResultNode> analysisRes) {
        String[] xAxis = {"0.0", "0.2", "0.4", "0.6", "0.8", "1.0"};
        String[] yAxis = {"-1.0", "0.5", "0.0", "0.5", "1.0"};

        Float[][] items = new Float[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                items[i][j] = 0.0f;
            }
        }

        for (ResultNode node: analysisRes) {
            float salience = node.getSalience();
            int xIndex;
            if (salience <= 0.1f) {
                xIndex = 0;
            } else if (salience <= 0.3f) {
                xIndex = 1;
            } else if (salience <= 0.5f) {
                xIndex = 2;
            } else if (salience <= 0.7f) {
                xIndex = 3;
            } else if (salience <= 0.9f) {
                xIndex = 4;
            } else {
                xIndex = 5;
            }

            float score = node.getScore();
            int yIndex;
            if (score <= -0.75) {
                yIndex = 0;
            } else if (score <= -0.25) {
                yIndex = 1;
            } else if (score <= 0.25) {
                yIndex = 2;
            } else if (score <= 0.75) {
                yIndex = 3;
            } else {
                yIndex = 4;
            }

            items[xIndex][yIndex] += node.getMagnitude();
        }

        List<Number[]> results = new ArrayList<>(30);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                results.add(new Number[] {i, j, items[i][j]});
            }
        }

        Heatmap heatmap = new Heatmap()
                .addXAxis("salience", xAxis)
                .addYAxis("score", yAxis)
                .setVisualMap(0, 10)
                .addSeries(new HeatmapSeries().setName("magnitude")
                        .setData(results.toArray())
                        .setLabel(new SeriesLabel().setShow(true)));

        Engine engine = new Engine();
        return engine.renderJsonOption(heatmap);
    }
}
