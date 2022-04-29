package edu.cmu.cs.cs214.hw6.framework.plugin.data;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.model.*;
import edu.cmu.cs.cs214.hw6.framework.core.DataPlugin;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.youtube.YouTube;
import edu.cmu.cs.cs214.hw6.framework.utils.DataNode;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class YoutubeDataPlugin implements DataPlugin {
    // Place your YouTube API key here
    private static final String DEVELOPER_KEY = "AIzaSyBbI8osWXhhCKTsz4JPyRksWGraPj5LMMI";
    private static final String APPLICATION_NAME = "Youtube Data Plugin";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private YouTube service;
    private CommentThreadListResponse apiResponse;

    @Override
    public String toString() {
        return "Youtube";
    }

    @Override
    public void connectToSource() throws GeneralSecurityException, IOException {
        service = getService();
    }

    @Override
    public void extractData(String Query) throws IOException, GeneralSecurityException {
            getResponse(Query);
    }

    @Override
    public List<DataNode> processData() {
        List<CommentThread> items = apiResponse.getItems();
        List<DataNode> dataNodes = new ArrayList<>();

        for (CommentThread item : items) {
            String comment = item.getSnippet().getTopLevelComment().getSnippet().getTextOriginal();
            dataNodes.add(new DataNode(null, comment));
        }
        return dataNodes;
    }

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    private YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Executes API request given a video id and returns a response
     * @param video_id the ID of a YouTube video to search for
     */
    private void getResponse(String video_id) throws IOException {
        YouTube youtubeService = service;
        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads().list(List.of("snippet"));
        apiResponse = request.setKey(DEVELOPER_KEY)
                              .setMaxResults(20L)
                              .setTextFormat("plainText")
                              .setVideoId(video_id)
                              .execute();
    }
}