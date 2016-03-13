package com.roberterrera.foodpoisoning;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hod.api.hodclient.HODApps;
import hod.api.hodclient.HODClient;
import hod.api.hodclient.IHODClientCallback;
import hod.response.parser.FindRelatedConceptsResponse;
import hod.response.parser.HODErrorCode;
import hod.response.parser.HODErrorObject;
import hod.response.parser.HODResponseParser;

public class MainActivity extends Activity implements IHODClientCallback {

    private HODClient hodClient;
    HODResponseParser hodParser = new HODResponseParser();
    String userQuery;

    String findRelatedConceptsQueryURL = "https://api.havenondemand.com/1/api/sync/findrelatedconcepts/v1?text=chipotle%2C+food+poisoning&indexes=news_eng&min_score=75&sample_size=10&apikey=50d036f8-e46f-4242-aac6-9650cf6ad645";
    String findSimilarQueryURL = "https://api.havenondemand.com/1/api/sync/findsimilar/v1?text=Chipotle%2C+food+poisoning&indexes=news_eng&max_page_results=10&min_score=75&summary=concept&total_results=false&apikey=50d036f8-e46f-4242-aac6-9650cf6ad645";
    // TODO: Add member variables for Views.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // For testing purposes:
//        userQuery = "meat%2C+nausea%2C+new+york";

        // TODO: Connect views to xml files.

        // Create new instance of HODClient that uses the api key and callback context.
        hodClient = new HODClient(HODClient.apiKey, this);
        QueryAsyncTask queryAsyncTask = new QueryAsyncTask();
        queryAsyncTask.execute(findSimilarQueryURL);
//        useHODClient();
    }

    @Override
    public void onErrorOccurred(String errorMessage){
    // TODO: Handle an error.
    }


    // Leverages the "Find related concepts" API to search the specified news sites below for concepts related to the user's text query.
 /*
    private void useHODClient(){
        String hodApp = HODApps.FIND_RELATED_CONCEPTS;
        Map<String,Object> params =  new HashMap<String,Object>();
        params.put("url", "http://www.cnn.com");
        params.put("url", "http://www.bbc.com");
        params.put("url", "http://www.nytimes.com");
        params.put("url", "http://www.huffingtonpost.com");
        List<String> entities = new ArrayList<String>();
        entities.add("title_eng");
        entities.add("summary_eng");
        params.put("query", userQuery);
        params.put("unique_entities", "true");

        hodClient.GetRequest(params, hodApp, HODClient.REQ_MODE.SYNC);
    }
*/

//    private void useHODClient(){
//        String hodApp = HODApps.FIND_SIMILAR;
//        Map<String,Object> params =  new HashMap<String,Object>();
//        List<String> urls = new ArrayList<String>();
//        urls.add("http://www.huffingtonpost.com");
//        urls.add("http://www.bbc.com");
//        params.put("url", urls);
//        params.put("unique_entities", "true");
//        List<String> entities = new ArrayList<String>();
//        entities.add("title_eng");
//        entities.add("summary_eng");
//        params.put("text", userQuery);
//        params.put("entity_type", entities);
//
//        hodClient.GetRequest(params, hodApp, HODClient.REQ_MODE.SYNC);
//    }

    // TODO: Connect Get Content api


    @Override
    public void requestCompletedWithContent(String response) {

    }

    /*   @Override
       public void requestCompletedWithContent(String response) {
           Toast.makeText(MainActivity.this, "Made it to CompletedWithContent!", Toast.LENGTH_SHORT).show();

           String text = "";
           FindRelatedConceptsResponse resp = (FindRelatedConceptsResponse) hodParser.
                   ParseCustomResponse(FindRelatedConceptsResponse.class, response);
           if (resp != null) {
               for (FindRelatedConceptsResponse.Entities entities : resp.entities) {
                   // access document field ...
                   // e.g. doc.reference
                   if (entities.text != null) {
                       text += "Statement: " + entities.text + "\n";
                       //print result
                       Log.d("COMPLETE_WITH_CONTENT", response);
                   }
               }
           } else { // check status or handle error
               List<HODErrorObject> errors = hodParser.GetLastError();
               String errorMsg = "Parsing failed :(";
               for (HODErrorObject err: errors) {
                   if (err.error == HODErrorCode.QUEUED) {
                       // sleep for a few seconds then check the job status again
                       hodClient.GetJobStatus(err.jobID);
                       return;
                   } else if (err.error == HODErrorCode.IN_PROGRESS) {
                       // sleep for for a while then check the job status again
                       hodClient.GetJobStatus(err.jobID);
                       return;
                   } else {
                       errorMsg += String.format("Error code: %d\nError Reason: %s\n", err.error, err.reason);
                       if (err.detail != null)
                           errorMsg += "Error detail: " + err.detail + "\n";
                   }
                  // print error message.

               }
           }
       }
                   */
    @Override
    public void requestCompletedWithJobID(String response){ }

    private class QueryAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... myUrl) {

            String contentAsString = "";
            InputStream inputStream = null;

            try {
                URL url = new URL(myUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                // Starts the query
                conn.connect();
                inputStream = conn.getInputStream();

                // Converts the InputStream into a string
                contentAsString = getInputStream(inputStream);

            } catch (Throwable thr) {
                thr.printStackTrace();
            }

            try {
                // Get the data inside the JSON object, and the data inside the object's array.
                JSONObject dataObject = new JSONObject(contentAsString); // Could take no params, or could take the string you want to use.
//                JSONArray itemJsonArray = dataObject.getJSONArray("entities"); // Getting the array gets the stuff inside the object.
                JSONArray documentsJsonArray = dataObject.getJSONArray("documents");

                // For every object in the item array, add the name to the ArrayList.
                for (int i = 0; i < documentsJsonArray.length(); i++) {
                    JSONObject object = documentsJsonArray.optJSONObject(i);
//                    String text = object.optString("text");
                    String reference = object.optString("reference");
                    String title = object.optString("title");
                    String summary = object.optString("summary");

                    Log.d("QUERY_ASYNC_TASK", "Title: "+title+", Summary: "+summary+", Reference: "+reference);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public String getInputStream(InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String read;

        while((read = br.readLine()) != null) {
            sb.append(read);
        }

        br.close();
        return sb.toString();
    }

}
