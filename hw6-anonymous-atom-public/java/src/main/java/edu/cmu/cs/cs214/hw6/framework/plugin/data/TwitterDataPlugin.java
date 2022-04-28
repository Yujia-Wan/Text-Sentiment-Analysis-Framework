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

public class TwitterDataPlugin implements DataPlugin {
    // Set environment variable for Twitter
    private final String bearerToken = System.getenv("BEARER_TOKEN");
    private String apiResponse;

    public static final int RESPONSE_SUBSTRING_START = 2;
    public static final int RESPONSE_SUBSTRING_END = 6;

    @Override
    public String toString() {
        return "Twitter";
    }

    @Override
    public void connectToSource() {
    }

    /*
     * Extract the latest 100 tweets from a given Twitter user and set the extracted data as JSON string the
     * field of apiResponse
     * */
    @Override
    public void extractData(String Query) {
        // Replace with user ID below
        String response = null;
        try {
            response = getTweets(Query, bearerToken);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        if (response.substring(RESPONSE_SUBSTRING_START, RESPONSE_SUBSTRING_END).equals("data")) {
            apiResponse = response;
        } else {
            apiResponse = null;
        }
    }

    /*
     * This method calls the v2 User Tweet timeline endpoint by user ID
     * */
    private String getTweets(String userId, String bearerToken) throws IOException, URISyntaxException {
        String tweetResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/%s/tweets", userId));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("max_results", "100"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
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
        JSONArray listOfTexts = response.getJSONArray("data");
        for (Object text : listOfTexts) {
            JSONObject textJson = (JSONObject) text;
            String entityText = (String) textJson.get("text");
            listOfDataNodes.add(new DataNode(null, entityText));
        }
        return listOfDataNodes;
    }
}
