package edu.cmu.cs.cs214.hw6.framework.plugin.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.cmu.cs.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs.cs214.hw6.framework.utils.DataNode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class NewsDataPlugin implements DataPlugin {
    private final String apiKey = "eef1a9587ac042ab8f0d9da0b3d109b4";
    private String apiResponse;

    @Override
    public String toString() {
        return "News";
    }

    @Override
    public void connectToSource() {
    }

    @Override
    public void extractData(String query) {
        String response = null;
        try {
            response = getNews(query, apiKey);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        apiResponse = response;
    }

    private String getNews(String topic, String apiKey) throws IOException, URISyntaxException {
        String newsResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder(String.format("https://newsapi.org/v2/everything?q=%s&language=en&apiKey=%s",
                topic, apiKey));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("max_results", "20"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            newsResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return newsResponse;
    }

    /*
     * Transfer the response as JSON string into a list of DataNode which can be processed later by our framework
     * */
    @Override
    public List<DataNode> processData() {
        List<DataNode> listOfDataNodes = new ArrayList<>();
        if (apiResponse == null) {
            return listOfDataNodes;
        }
        JSONObject response =  JSONObject.parseObject(apiResponse);
        JSONArray listOfTexts = response.getJSONArray("articles");
        for (Object text : listOfTexts) {
            JSONObject textJson = (JSONObject) text;
            String entityText = (String) textJson.get("description");
            listOfDataNodes.add(new DataNode(null, entityText));
        }
        return listOfDataNodes;
    }
}
