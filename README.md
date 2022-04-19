# hw6-analytics-framework-youshallpass

## Starting a Framework
### Prerequisites
You will need a [Google Cloud Platform Console](https://console.cloud.google.com/apis/dashboard) project with the Natural Language [API enabled](https://console.cloud.google.com/apis/enableflow?apiid=language.googleapis.com&project=sentiment-analysis-347302). You will need to [enable billing](https://cloud.google.com/apis/docs/getting-started#enabling_billing) to use Google Natural Language. [Follow these instructions](https://cloud.google.com/resource-manager/docs/creating-managing-projects) to get your project set up. You will also need to set up the local development environment by [installing the Google Cloud SDK](https://cloud.google.com/sdk/) and running the following commands in command line: ```gcloud auth login``` and ```gcloud config set project [YOUR PROJECT ID]```.

To simplify, you can use the JSON credentials file provided in the src/main/resources/META-INF/services/ directory and ignore the Authentication section.

### Authentication
See the [Authentication](https://github.com/googleapis/google-cloud-java#authentication) section in the base directory's README.
#### Using a service account (recommended)
1. [Generate a JSON service account key](https://cloud.google.com/storage/docs/reference/libraries#setting_up_authentication).
2. After downloading that key, supply the JSON credentials file when building the service options: in FrameworkImpl.java analyzeTextSentiment() (line 68), change the file path to the JSON file you generated.

### Set Up Backend Server
Either run the Java backend by using your IDE or by typing
``` 
mvn site
mvn exec:exec
```
in the backend folder

### Set Up Frontend Server
In the frontend folder, run
```
npm install
npm start
```
This will start a server at http://localhost:3000

### Fixing Handlebars
There is an error with Handlebars working with Webpack. A simple fix for this error: after you run ```npm install```, this creates a node_modules folder within the frontend folder. Then, in that folder, find the Handlebars folder, and go to its package.json. You will find in this package.json, there is a section called ```browser```. Add to this dictionary the line ```"fs": false```.

### GUI
- Select data source. Now we support Twitter/YouTube.
- Input search index. For Twitter, input Twitter username (like "CarnegieMellon"). For YouTube, input video url (if url is https://www.youtube.com/watch?v=Hp_Eg8NMfT0, you should input "Hp_Eg8NMfT0").
- Select display chart. Now we support Bar Chart/Pie Chart.

## Adding Data Plugin
We use a String search index to retrieve texts (like a username for Twitter, a video url for YouTube). The retrieved data will be stored in a list of Data objects. Refer to documentation of DataPlugin interface for more details.

To register your plugin with the framework, add the fully-qualified class name of your plugin to the edu.cmu.cs214.hw6.framework.core.DataPlugin file in the src/main/resources/META-INF/services/ directory.

## Adding Display Plugin
After performing sentiment analysis, the list of Data will be aggregated and calculated to achieve visualized data. The result data should be formatted as an JSONObject which will be sent to frontend and embedded in the web-based GUI. Refer to documentation of DisplayPlugin interface for more details.

To register your plugin with the framework, add the fully-qualified class name of your plugin to the edu.cmu.cs214.hw6.framework.core.DisplayPlugin file in the src/main/resources/META-INF/services/ directory.

## Data Processing
we use [Google's Natural Language API](https://cloud.google.com/natural-language/docs/analyzing-sentiment) to perform sentiment analysis (how positive, negative, or neutral the tone of some text is) on different texts.