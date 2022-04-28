package edu.cmu.cs.cs214.hw6.framework.plugin.visualization;
import edu.cmu.cs.cs214.hw6.framework.core.VisPlugin;
import edu.cmu.cs.cs214.hw6.framework.utils.ResultNode;
import org.icepear.echarts.Radar;
import org.icepear.echarts.charts.radar.RadarDataItem;
import org.icepear.echarts.components.coord.radar.RadarIndicator;
import org.icepear.echarts.render.Engine;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class RadarVisPlugin implements VisPlugin {
    @Override
    public String toString() {
        return "Radar";
    }

    @Override
    public List<ResultNode> processData(List<ResultNode> nodes) {
        nodes.sort(new Comparator<ResultNode>() {
            @Override
            public int compare(ResultNode o1, ResultNode o2) {
                return Float.compare(o1.getMagnitude(), o2.getMagnitude());
            }
        });
        return nodes;
    }

    @Override
    public String generateChartInfo(List<ResultNode> nodes) {
        int size = nodes.size();

        List<Number[]> data = new ArrayList<>(); // each Number[] contains three values associated with a text
        String[] entities = new String[size]; // all the entities

        for (int i = 0; i < size; i++) {
            Number[] item = new Number[3];
            item[0] = nodes.get(i).getSalience();
            item[1] = nodes.get(i).getScore();
            item[2] = nodes.get(i).getMagnitude();
            data.add(item);
            entities[i] = nodes.get(i).getEntity();
        }

        List<RadarDataItem> radarDataItems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            RadarDataItem item = new RadarDataItem();
            item.setValue(data.get(i)).setName(entities[i]);
            radarDataItems.add(item);
        }

        // Create radar chart options
        Radar radar = new Radar()
                .setLegend()
                .setRadarAxis(new RadarIndicator[] {
                        new RadarIndicator().setName("salience").setMax(1), // salience value should be under 1
                        new RadarIndicator().setName("score").setMax(1),// score value should be under 1
                        new RadarIndicator().setName("magnitude").setMax(1)})// magnitude value should be under 1
                .addSeries(radarDataItems.toArray());

        Engine engine = new Engine();
        return engine.renderJsonOption(radar);
    }
}
