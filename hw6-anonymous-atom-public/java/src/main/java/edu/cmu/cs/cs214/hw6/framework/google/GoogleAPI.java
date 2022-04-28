/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.cmu.cs.cs214.hw6.framework.google;

import com.alibaba.fastjson.JSONObject;
import com.google.cloud.language.v1.*;
import com.google.cloud.language.v1.Document.Type;

/**
 * A sample application that uses the Natural Language API to perform entity, sentiment and syntax
 * analysis.
 */
public class GoogleAPI {

    /** Identifies the sentiment in the string {@code text}. */
    public static JSONObject analyzeSentimentText(String text) throws Exception {
        // [START language_sentiment_text]
        // Instantiate the Language client com.google.cloud.language.v1.LanguageServiceClient
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
            AnalyzeSentimentResponse response = language.analyzeSentiment(doc);
            Sentiment sentiment = response.getDocumentSentiment();

            if (sentiment.getMagnitude() < 0.5) {
                return null;
            }

            JSONObject res = new JSONObject();
            res.put("magnitude", sentiment.getMagnitude());
            res.put("score", sentiment.getScore());
            res.put("salience", 1.0);
            return res;
        }
        // [END language_sentiment_text]
    }

    /** Detects the entity sentiments in the string {@code text} using the Language Beta API. */
    public static JSONObject entitySentimentText(String text) throws Exception {
        // [START language_entity_sentiment_text]
        // Instantiate the Language client com.google.cloud.language.v1.LanguageServiceClient
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
            AnalyzeEntitySentimentRequest request =
                    AnalyzeEntitySentimentRequest.newBuilder()
                            .setDocument(doc)
                            .setEncodingType(EncodingType.UTF16)
                            .build();
            // detect entity sentiments in the given string
            AnalyzeEntitySentimentResponse response = language.analyzeEntitySentiment(request);

            if (response.getEntitiesList().size() == 0) {
                return null;
            }
            Entity entity = response.getEntities(0);
            if (entity.getSalience() < 0.5) {
                return null;
            }
            JSONObject result = new JSONObject();

            result.put("entity", entity.getName());
            if (entity.getSentiment().getMagnitude() < 0.5) {
                return null;
            }
            result.put("salience", entity.getSalience());
            result.put("score", entity.getSentiment().getScore());
            result.put("magnitude", entity.getSentiment().getMagnitude());
            return result;
        }
    }
}
