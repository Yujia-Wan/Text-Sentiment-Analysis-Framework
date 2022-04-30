<p align="center" style="font-size: 30px; font-weight: bold;"> hw6-analytics-framework-anonymous-atom </p>


> <p align="center" style="font-size: 25px; font-weight: bold;"> Text Sentiment Analysis Framework </p>
>
> - This framework performs sentiment analysis on texts and, optionally, finds the entities implied in the given texts. In other words, our framework is able to identify the sentiment towards an entity implied in the given text. Furthermore, it provides graphical representation of the sentiment analysis.
> - <strong>Sequence</strong>
>   1. Extracts texts from a source (using data plugins)
>   2. Identifies the most salient entity in each text (skips this step if the entity is already provided by user)
>   3. Performs the sentiment analysis on each text; for each text generates three scores based on three criteria:
>      - <strong>salience:</strong> importance or relevance of this entity to the text
>      - <strong>score:</strong> overall emotional leaning of the text
>      - <strong>magnitude:</strong> overall strength of emotion (both positive and negative) within the given text
>   3. Provides graphical representation of the data produced in the sentiment analysis (using visualization plugins).
---

> <p align="center" style="font-size: 25px; font-weight: bold;">SETUP</p>
>
> If you have trouble setting up APIs, please contact us.
> - Obtain permission from these APIs (Youtube and Twitter are optional as they are sample plugins)
>   - Google's Natural Language API
>   - Twitter API
>   - Youtube Data API
>
> - <strong>Youtube Data API</strong> (https://developers.google.com/youtube/v3/getting-started for more details)
>   - You need a Google Account to access the Google API Console
>   - Create a project in the Google Developers Console at https://console.developers.google.com/. Please choose "No organization" for "Organization"
>   - Obtain your authorization credential, an API key, not Oauth 2.0, at https://console.developers.google.com/apis/credentials so your application can submit API requests.
>   - Go to the API Console at https://console.developers.google.com/ and select the project that you just registered
>   - Visit the Enabled APIs page at https://console.developers.google.com/apis/enabled. In the list of APIs, make sure the status is ON for the YouTube Data API v3.
>   - Copy your API key and place it in plugin/data/YoutubeDataPlugin.java where it says "Place your YouTube API key here"
>
> - <strong>Twitter API</strong> (https://developer.twitter.com/en/docs/authentication/oauth-2-0/bearer-tokens for more details)
>   - Sign up for a developer account at https://developer.twitter.com/en/portal/petition/essential/basic-info
>   - Create a Twitter App at https://developer.twitter.com/content/developer-twitter/en/docs/basics/developer-portal/guides/apps
>   - Obtain the API key found in the developer portal following the steps below
>     - Login to your Twitter account on developer.twitter.com
>     - Navigate to the Twitter App dashboard at https://developer.twitter.com/content/developer-twitter/en/apps and open the Twitter App for which you would like to generate access tokens
>     - Navigate to the "keys and tokens" page
>     - You'll find your Bearer Token on this page
>     - Add it to your environment variables: BEARER_TOKEN="YOUR_ACTUAL_TOKEN"
> - <strong>Google Cloud Natural Language API</strong> (https://cloud.google.com/natural-language/docs/setup for more details)
>   - Go to the Google Cloud Console at https://support.google.com/cloud/answer/3465889?hl=en&ref_topic=3340599 to set up and manage Natural Language resources
>   - Create a project to use services provided by Google Cloud
>   - Enable billing, a billing account used to define who pays for a given set of resources is needed
>   - Enable the Cloud Natural Language API for your project
>   - Set up authentication
>     - Create a service account and download the private key file
>     - Use the service account key file in your environment
>   - Install the gcloud CLI following https://cloud.google.com/sdk/docs/install. Different systems install it differently.
>   - Initialize the gcloud CLI following https://cloud.google.com/sdk/docs/initializing.
> - <strong>New York Times API</strong> (https://developer.nytimes.com/ for more details)
>   - Create an account to [get started](https://developer.nytimes.com/get-started)
>   - Sign in your account on developer.nytimes.com
>   - Create a new app
>   - Save your API key and place it in framework/plugin/data/NewYorkTimesDataPlugin.java where it says "fill in your key here"
> - <strong>News API</strong> (https://newsapi.org/docs/get-started for more details)
>   - Use your e-mail address to get an account
>   - Sign in your account and [get the api key](https://newsapi.org/account)
>   - Save your API key and place it in framework/plugin/data/NewsDataPlugin.java in line 25
- <strong>You are good to go</strong>
---

> <p align="center" style="font-size: 25px; font-weight: bold;"> HOW TO RUN THE PROGRAM & NAVIGATE THE WEBSITE (GUI) </p>
>
> #### STEP 1
> - Enter the hw6-analytics-framework-anonymous-atom/java folder, and run the following command
>   - mvn install
>   - mvn exec:java -D"exec.mainClass"="edu.cmu.cs.cs214.hw6.framework.App"
>   - Point your browser to http://localhost:8080/
> #### You should see
> <img src="https://i3.lensdump.com/i/t0hNHP.png" alt="t0hNHP.png" border="0" />
>
> #### STEP 2
> - Select a data plugin (sample plugins: Twitter and YouTube)
>
> #### STEP 3
> - Enter your search query in the input box.
> - Hit "add"
> **CAREFUL**  Please provide one search query at a time. Must select a data plugin before you hit "Add". Wait until the wheel in the browser tab stops spinning, or the new ID appears in the list of added IDs, to add another query. If you enter a non-existing ID, it will not be added.
> - Twitter user IDs for your convenience (https://commentpicker.com/twitter-id.php helps you find more)
>   - Barack Obama: 50393960, Jeff Bezos: 15506669, Chris Pratt: 241382835, CNN Breaking News: 428333, Fox News: 1367531
> - Youtube video IDs for your convenience (you can find the video ID after "v=" in any Youtube link)
>   - Will Smith apologizes: nSQBnF3uR50, Italians try American food: pHJhB4pmPCg, COVID vaccines: the81FQoAUI, Elon Musk tries to buy Twitter: JhAe2y81GDE
> - New York Times search keywords for your convenience to look up articles by keyword
>   - "election", "smile", "university"
> - News topic for you convenience
>   - "carnegie", "cmu"
>
> #### STEP 4
> - Select a visualization plugin.  
> For example, If you select Bar option, the results from the sentimental analysis will first be sorted based on the sentimental magnitude, and then be visualized in a Bar chart. Consider the combination of Twitter data plugin and Bar visualization plugin. The x axis lists the salient entities that the Twitter user talks/cares about. On the y axis, these salient entities are sorted based on the sentimental magnitude, representing the overall emotional strength towards the entity, and the sentimental score indicates the positive or negative emotional lean.
>
> #### STEP 4
> - Hit "visualize" button to visualize the data
---

> <p align="center" style="font-size: 25px; font-weight: bold; color: red;">CAREFUL !</p>
> When playing with the Youtube plugin, do not give it IDs of videos whose comments are turned off or videos that has comments not in English. Google's Natural Language API will likely fail on comments in other languages.
---

> <p align="center" style="font-size: 25px; font-weight: bold;"> ADDING PLUGINS</p>
>
> - **Data Plugins**
    >   Please create new classes in plugin/data/. They should implement the interface DataPlugin (for more details, please see Javadoc)
> - **Visualization Plugin**
    >   Please create new classes in plugin/visualization/. They should implement the interface VisPlugin (for more details, please see Javadoc)
> - **Add your new plugins in resources/META-INF.services folder
---

> <p align="center" style="font-size: 25px; font-weight: bold;">COMMUNICATION BETWEEN BACKEND & FRONTEND</p>
>
>   - Read on only if you want to add more template variables in the frontend
>   - The project structure follows the example from recitation 10 of the fall version (https://piazza.com/class/ky93mfn2pytyv?cid=854)
> - **DataAnalysisState**, in "gui" package
>   - Any template variable you want the frontend(game_template.hbs) to receive **must** become the instance variable of this class first, else it won't be delievered to the frontend.
>   - Every instance variable **must** have a GETTER method of its own, else it won't be delievered to the frontend.
---

> <p align="center" style="font-size: 25px; font-weight: bold;"> APIs used </p>
>
> - Sample data plugins: Twitter API, Youtube API
> - Sentiment analysis: Google's Natual Language API
---

> <p align="center" style="font-size: 25px; font-weight: bold;"> INTERFACES & CLASSES EXPOSED TO PLUGINS</p>
>
> - **Interfaces**
>   - **DataPlugin**, in "core" package
>   - **VisPlugin**, in "core" package
> - **Classes**
>   - **DataFrameworkImpl**, in "core" package
>
>     Notice that the interface DataFramework does not interact with the plugins directly.
>   - **DataNode**, in "utils" package
>
>     Texts collected from the data source are packed into this data structure.
>     - "entity" field: entity (proper nouns such as public figures, landmarks, etc) of a text. If no entity is provided, make it null and later the framework will determine it.
>     - "text" field: the given text
>   - **ResultNode**, in "utils" package
>
>     A data node along with its corresponding sentiment analysis will be packed into this data structure.
>     - "entity" field: explained earlier
>     - "magnitude" field: overall strength of emotion (both positive and negative) within the given text, between 0.0 and +inf
>     - "score" field: ranges between -1.0 (negative) and 1.0 (positive) and corresponds to the overall emotional leaning of the text
>     - "salience" field: relevance to the overall text
>     - See https://cloud.google.com/natural-language/docs/basics for more info
>
> - **Exchanged data structure**
>   - **a data plugin --> DataFrameworkImpl**:  List< DataNode >
>   - **DataFrameworkImpl --> a visualization plugin**: List< ResultNode >
>   - **a visualization plugin --> DataFrameworkImpl**: JSON in string format
>   - **DataFrameworkImpl --> DataAnalysisState**: the same JSON to be sent to the frontend and used by Javascript