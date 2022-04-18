package edu.cmu.cs214.hw6.framework.core;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceSettings;
import com.google.cloud.language.v1.Sentiment;
import edu.cmu.cs214.hw6.dataPlugin.Data;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FrameworkImpl implements Framework {
    private DataPlugin currentDataPlugin;
    private String dataPluginIndex;
    private DisplayPlugin currentDisplayPlugin;
    private List<DataPlugin> registeredDataPlugins;
    private List<DisplayPlugin> registeredDisplayPlugins;
    private JSONObject result;

    public FrameworkImpl() {
        this.registeredDataPlugins = new ArrayList<>();
        this.registeredDisplayPlugins = new ArrayList<>();
    }

    /**
     * Registers a new {@link DataPlugin} with the framework.
     *
     * @param plugin Plugin to be registered.
     */
    public void registerDataPlugin(DataPlugin plugin) {
        plugin.onRegister(this);
        registeredDataPlugins.add(plugin);
    }

    /**
     * Registers a new {@link DisplayPlugin} with the framework.
     *
     * @param plugin Plugin to be registered.
     */
    public void registerDisplayPlugin(DisplayPlugin plugin) {
        plugin.onRegister(this);
        registeredDisplayPlugins.add(plugin);
    }

    /**
     * Analyzes text message sentiment using Google's Natural Language API.
     *
     * @param data A list of texts data to be analyzed.
     * @return Sentiment analysis result.
     */
    @Override
    public List<Data> analyzeTextSentiment(List<Data> data) {
        try {
            // Authentication
            // Change the file path to the json file you generated.
            CredentialsProvider credentialsProvider = FixedCredentialsProvider
                    .create(ServiceAccountCredentials.fromStream(
                            new FileInputStream("src/main/resources/sentiment-analysis-347302-56034cc1688f.json")));

            LanguageServiceSettings languageServiceSettings = LanguageServiceSettings.newBuilder()
                    .setCredentialsProvider(credentialsProvider).build();

            // Instantiate the Language client com.google.cloud.language.v1.LanguageServiceClient
            LanguageServiceClient languageServiceClient = LanguageServiceClient.create(languageServiceSettings);
            for (Data d: data) {
                String text = d.getText();
                Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
                AnalyzeSentimentResponse response = languageServiceClient.analyzeSentiment(doc);
                Sentiment sentiment = response.getDocumentSentiment();
                if (sentiment == null) {
                    System.out.println("No sentiment found");
                } else {
//                    System.out.printf("Sentiment magnitude: %.3f\n", sentiment.getMagnitude());
//                    System.out.printf("Sentiment score: %.3f\n", sentiment.getScore());
                    d.setScore(sentiment.getScore());
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("Cannot load file from given path.");
        } catch (IOException ioe) {
            System.err.println("Fail to perform sentiment analysis.");
        }
        return data;
    }

    /**
     * Processes data and calculates display data after client provides necessary keywords.
     *
     * @param dataPlugin Data plugin.
     * @param dataPluginIndex Keyword for data plugin to retrieve.
     * @param displayPlugin Display plugin.
     */
    @Override
    public void process(DataPlugin dataPlugin, String dataPluginIndex, DisplayPlugin displayPlugin) {
        setCurrentDataPlugin(dataPlugin);
        setDataPluginIndex(dataPluginIndex);
        setCurrentDisplayPlugin(displayPlugin);

        List<Data> data = this.currentDataPlugin.getRetrievedData(dataPluginIndex);
        data = analyzeTextSentiment(data);
        this.result = this.currentDisplayPlugin.getVisualizedData(data);
    }

    public List<String> getRegisteredDataPluginName() {
        return registeredDataPlugins.stream().map(DataPlugin::getDataPluginName).collect(Collectors.toList());
    }

    public List<String> getRegisteredDisplayPluginName() {
        return registeredDisplayPlugins.stream().map(DisplayPlugin::getDisplayPluginName).collect(Collectors.toList());
    }

    public String getCurrentDataPlugin() {
        return this.currentDataPlugin.getDataPluginName();
    }

    public String getDataPluginIndex() {
        return this.dataPluginIndex;
    }

    public String getCurrentDisplayPlugin() {
        return this.currentDisplayPlugin.getDisplayPluginName();
    }

    public String getResult() {
        return this.result.toString();
    }

    public void setCurrentDataPlugin(DataPlugin plugin) {
        this.currentDataPlugin = plugin;
    }

    public void setDataPluginIndex(String index) {
        this.dataPluginIndex = index;
    }

    public void setCurrentDisplayPlugin(DisplayPlugin plugin) {
        this.currentDisplayPlugin = plugin;
    }
}
