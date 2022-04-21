package edu.cmu.cs214.hw6.dataPlugin;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.Framework;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class TwitterPlugin implements DataPlugin {
    private static final String DATA_PLUGIN_NAME = "Twitter";
    private static final String BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAALyTbQEAAAAAHy0tBRfnqAUokF%2Bj7%2FbsdJn2xmM%" +
            "3DJPJJyuVBjfCtlNgmZ7jQDSnNtZ8hC6XOR694l5VIiXPyCqUdrN";
    private Framework framework;

    /**
     * Calls the v2 Users endpoint with username as query parameter.
     *
     * @param username Twitter username.
     * @param bearerToken Bearer token.
     * @return User ID.
     * @throws IOException I/O failure.
     * @throws URISyntaxException Invalid URI.
     */
    private String getUser(String username, String bearerToken) throws IOException, URISyntaxException {
        String userId = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/users/by");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("usernames", username));
        queryParameters.add(new BasicNameValuePair("user.fields", "pinned_tweet_id"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            try {
                String userResponse = EntityUtils.toString(entity, "UTF-8");
                JSONObject json = new JSONObject(userResponse);
                JSONArray tokenList = json.getJSONArray("data");
                JSONObject token = tokenList.getJSONObject(0);
                userId = token.getString("id");
            } catch (JSONException e) {
                System.err.println("Could not find user with username: " + username);
                this.framework.setInstruction("Could not find user with username: " + username);
            }
        }
        return userId;
    }

    /**
     * Calls the v2 User Tweet timeline endpoint by user ID.
     *
     * @param userId User ID.
     * @param bearerToken Bearer token.
     * @return A list of tweets text data (default: 10 tweets).
     * @throws IOException I/O failure.
     * @throws URISyntaxException Invalid URI.
     */
    private List<Data> getTweets(String userId, String bearerToken) throws IOException, URISyntaxException {
        List<Data> result = new ArrayList<>();

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/%s/tweets", userId));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            String tweetResponse = EntityUtils.toString(entity, "UTF-8");
            JSONObject json = new JSONObject(tweetResponse);
            try {
                JSONArray tokenList = json.getJSONArray("data");
                for (int i = 0; i < tokenList.length(); i++) {
                    JSONObject token = tokenList.getJSONObject(i);
                    String tweet = token.getString("text");
                    String time = token.getString("created_at");
                    Data data = new Data(tweet, time, 0);
                    result.add(data);
                }
            } catch (JSONException e) {
                System.err.println("This user hasn't post any tweet yet.");
                if (this.framework.getInstruction().equals("")) {
                    this.framework.setInstruction("This user hasn't post any tweet yet.");
                }
            }
        }
        return result;
    }

    @Override
    public String getDataPluginName() {
        return DATA_PLUGIN_NAME;
    }

    @Override
    public List<Data> getRetrievedData(String username) {
        List<Data> tweets = new ArrayList<>();
        try {
            String userId = getUser(username, BEARER_TOKEN);
            tweets = getTweets(userId, BEARER_TOKEN);
        } catch (IOException | URISyntaxException e) {
            System.err.println("Fail to get tweets from given username.");
        }
        return tweets;
    }

    @Override
    public void onRegister(Framework framework) {
        this.framework = framework;
    }
}
