package edu.cmu.cs.cs214.hw6.framework.plugin.data;

import edu.cmu.cs.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs.cs214.hw6.framework.utils.DataNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class NewYorkTimesDataPlugin implements DataPlugin {
    // fill in your key here
    private static final String KEY = "1bOUGUxQ4PF2SHDI4U1ELLq6bjAspdzR";
    private String apiResponse;

    @Override
    public String toString() {
        return "New York Times";
    }

    @Override
    public void connectToSource() throws GeneralSecurityException, IOException {
    }

    @Override
    public void extractData(String query) throws IOException, GeneralSecurityException {
        String url_str = String.format("https://api.nytimes.com/svc/search/v2/articlesearch.json?q=%s&api-key=%s", query, KEY);
        HttpRequest request = HttpRequest.newBuilder(URI.create(url_str)).build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            apiResponse = response.body();
        } catch (InterruptedException e) {
            System.err.println("This operation is interrupted!");
        }
    }

    @Override
    public List<DataNode> processData() {
        List<DataNode> nodes = new ArrayList<>();
        if (apiResponse == null) {
            return nodes;
        }

        try {
            JSONObject response = new JSONObject(apiResponse);
            JSONArray texts = response.getJSONObject("response").getJSONArray("docs");
            for (int i = 0; i < texts.length(); i++) {
                String text = texts.getJSONObject(i).getString("abstract");
                nodes.add(new DataNode(null, text));
            }
        } catch (JSONException e) {
            System.err.println("Parse to JSONObject fails!");
        }
        return nodes;
    }
}
