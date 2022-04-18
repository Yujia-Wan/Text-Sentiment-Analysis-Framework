package edu.cmu.cs214.hw6.dataPlugin;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentSnippet;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.Framework;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Some code from https://developers.google.com/youtube/v3/docs/commentThreads/list
 */
public class YouTubePlugin implements DataPlugin {
    private static final String DATA_PLUGIN_NAME = "youtube";
    private static final String APPLICATION_NAME = "YouTube Data Plugin";
    private static final String DEVELOPER_KEY = "AIzaSyBbI8osWXhhCKTsz4JPyRksWGraPj5LMMI";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private Framework framework;
    private static final int NUMBER = 10;

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Override
    public String getDataPluginName() {
        return DATA_PLUGIN_NAME;
    }

    /**
     * Execute API request. Get the latest 20 comments from a video.
     * @param videoId videoId of YouTube video.
     * @return list of comment data.
     */
    @Override
    public List<Data> getRetrievedData(String videoId) {
        List<Data> dataSource = new ArrayList<>();
        try {
            YouTube youtubeService = getService();
            YouTube.CommentThreads.List request = youtubeService.commentThreads()
                    .list("snippet,replies");
            CommentThreadListResponse response = request.setKey(DEVELOPER_KEY)
                    .setVideoId(videoId)
                    .execute();
            List<CommentThread> commentList = response.getItems();
            for (CommentThread comment : commentList){
                CommentSnippet snippet = comment.getSnippet().getTopLevelComment().getSnippet();
                String text = snippet.getTextOriginal();
                String time = snippet.getPublishedAt().toString().substring(0, NUMBER);
                Data data = new Data(text, time, 0);
                dataSource.add(data);
            }
        } catch (GeneralSecurityException | IOException exception) {
            System.out.println("errorMessage : Error!");
            return null;
        }
        if (dataSource.size() == 0){
            System.out.println("errorMessage : No comment in this video.");
            return null;
        }
        return dataSource;
    }

    @Override
    public void onRegister(Framework framework) {
        this.framework = framework;
    }
}
