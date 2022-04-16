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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FrameworkImpl implements Framework {
    private DataPlugin currentDataPlugin;
    private DisplayPlugin currentDisplayPlugin;
    private List<DataPlugin> registeredDataPlugins;
    private List<DisplayPlugin> registeredDisplayPlugins;

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
     * Analyzes text message sentiment using Google Cloud Speech API.
     */
    public static Sentiment analyzeSentimentText(String text) throws IOException {
        // Authentication
        CredentialsProvider credentialsProvider = FixedCredentialsProvider
                .create(ServiceAccountCredentials.fromStream(new FileInputStream("/Users/yujiawang/key/sentiment-analysis-347302-56034cc1688f.json")));

        LanguageServiceSettings languageServiceSettings = LanguageServiceSettings.newBuilder()
                .setCredentialsProvider(credentialsProvider).build();

        // Instantiate the Language client com.google.cloud.language.v1.LanguageServiceClient
        try (LanguageServiceClient languageServiceClient = LanguageServiceClient.create(languageServiceSettings)) {
            Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
            AnalyzeSentimentResponse response = languageServiceClient.analyzeSentiment(doc);
            Sentiment sentiment = response.getDocumentSentiment();
            if (sentiment == null) {
                System.out.println("No sentiment found");
            } else {
                System.out.printf("Sentiment magnitude: %.3f\n", sentiment.getMagnitude());
                System.out.printf("Sentiment score: %.3f\n", sentiment.getScore());
            }
            return sentiment;
        }
    }

    // Google's Natural Language API test
//    public static void main(String... args) throws IOException {
//        // Authentication
//        CredentialsProvider credentialsProvider = FixedCredentialsProvider
//                .create(ServiceAccountCredentials.fromStream(new FileInputStream("/Users/yujiawang/key/sentiment-analysis-347302-56034cc1688f.json")));
//
//        LanguageServiceSettings languageServiceSettings = LanguageServiceSettings.newBuilder()
//                .setCredentialsProvider(credentialsProvider).build();
//
//        // Instantiates a client
//        LanguageServiceClient languageServiceClient = LanguageServiceClient.create(languageServiceSettings);
//
//        // The text to analyze
//        String[] texts = {"I love this!", "I hate this!"};
//        for (String text : texts) {
//            Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
//            // Detects the sentiment of the text
//            Sentiment sentiment = languageServiceClient.analyzeSentiment(doc).getDocumentSentiment();
//
//            System.out.printf("Text: \"%s\"%n", text);
//            System.out.printf(
//                    "Sentiment: score = %s, magnitude = %s%n",
//                    sentiment.getScore(), sentiment.getMagnitude());
//        }
//    }

    public List<String> getRegisteredDataPluginName() {
        return registeredDataPlugins.stream().map(DataPlugin::getDataPluginName).collect(Collectors.toList());
    }

    public List<String> getRegisteredDisplayPluginName() {
        return registeredDisplayPlugins.stream().map(DisplayPlugin::getDisplayPluginName).collect(Collectors.toList());
    }
}
