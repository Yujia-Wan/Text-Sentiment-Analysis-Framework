package edu.cmu.cs214.hw6;

public class NlpApiTest {
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
}
