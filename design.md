# Text Sentiment Analysis Framework

## Domain
Our topic is to perform sentiment analysis on different texts. The framework performs sentiment analysis on text from different sources which are provided by data plugins and use a visualization plugin to show the results in different ways. The reuse benefits of this framework come from the sentiment analysis itself and using existing visualizations when a new data plugin is provided or vice versa.

We would like to use [Google’s Natural Language API](https://cloud.google.com/natural-language/docs/analyzing-sentiment) for sentiment analysis.

This framework will support two types of plugins: data plugins and display plugins.

Data plugins could process requests from different sources and provide a list of text fragments with time stamps for the framework to perform sentiment analysis on. Data plugins could include:

* Twitter plugin that takes in Twitter messages with the [Twitter API](https://developer.twitter.com/en/docs/twitter-api).
* YouTube plugin that takes in comments from YouTube videos with the [YouTube Data API](https://developers.google.com/youtube/v3).

Display plugins could visualize data and sentiment analysis results provided by data plugins, after the framework does the processing. Display plugins could include:

* Pie chart displaying the proportion of different sentiments in text fragments using [Plotly Graphing Libraries](https://plotly.com/graphing-libraries/).
* Heat map displaying the magnitude for the most common sentiments and time of day using [Plotly Graphing Libraries](https://plotly.com/graphing-libraries/).

## Generality vs Specificity
This framework performs sentiment analysis on text from different sources and displays results in different ways. The reusable functionality is the sentiment analysis itself, which is the common part of the framework. We provide plugin interfaces for variable parts, which in this case are data plugins and display plugins. Therefore, common parts are separated from variable parts as framework, and variable plugins are entansible using the plugin interface.

The framework should act as a controller. It provides a web-based user interface, where clients can customize their data input and display requests. After receiving requests from clients, the framework parses the request and utilizes corresponding data plugin to acquire text fragments in a JSON format. We only provide intermediate JSON file format because we want to have some reuse like parser function in our code and don't make it too generic. Then the core NLP sentiment analysis is performed on the processed data, and the analysis results will be visualized as different chats on the user interface as client specified.

Data plugins take client’s data source requests as input and output a JSONObject with retrieved text fragments. More data plugins are supported but their output should be formatted as a JSON file. Display plugins take sentiment analysis results as input and output a JSON file using Plotly Graphing Libraries for frontend renderer to display visualized results. And more display plugins can be added to support different visualization needs. This framework is able to reuse existing visualizations when just providing a new data plugin and vice versa.

## Project Structure
![model](/resources/Model.png)
- Frontend
    - Use React and HTML.
- Backend
    - All the logic is stored in the backend. We use one controller to listen to the JSON  format data from the frontend.
    - NLP Plugin: help analyze the sentiment of the text. Return Map<String, String>.
    - Data Plugin: help extract data from different sources. Return a list of Strings.
    - Display Plugin: help visualize data, format the data in JSON, render to the frontend.
    - Plugin loading mechanism: use java.util.ServiceLoader which will uses Java reflection to instantiate plugin and register it with the framework.

## Plugin Interfaces
- Data Plugins
```

```
    - Twitter API
    ![TwitterPlugin](/resources/TwitterCodeSnippet.png)

    - YouTube Data API
      - Code snippet:
      ![YouTubePlugin](/resources/YouTubeCodeSnippet.png)
      - Key method:
      
        In CommentSnippet class:
        
        public String getTextDisplay()
        
        public DateTime getPublishedAt()

        public CommonSnippet setParentId(String parentId)
        
        ![Document](/resources/Document.png)
      - Sample test
      ![Test](/resources/Test.png)

- Display Plugins
```
```
    - [Plotly Open Source Graphing Libraries](https://plotly.com/graphing-libraries/)
    ![HeatMap](/resources/HeatMap.png)
