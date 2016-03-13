package com.roberterrera.foodpoisoning;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import hod.api.hodclient.HODClient;
import hod.api.hodclient.IHODClientCallback;
import hod.response.parser.HODResponseParser;

public class MainActivity extends Activity implements IHODClientCallback {

    private HODClient hodClient;
    HODResponseParser hodParser = new HODResponseParser();

    String symptom = "nausea";
    String location = "New York";
    String food = "meat";

    String reference;
    String title;
    String summary;

    String findSimilarQueryURL = "https://api.havenondemand.com/1/api/sync/findsimilar/v1?text="+symptom+"%2"+location+"%2"+food+"&indexes=news_eng&max_page_results=10&min_score=75&summary=concept&total_results=false&apikey=50d036f8-e46f-4242-aac6-9650cf6ad645";
    String getContentURL = "https://api.havenondemand.com/1/api/sync/getcontent/v1?index_reference=http%3A%2F%2F"+reference+"&indexes=news_eng&summary=concept&apikey=50d036f8-e46f-4242-aac6-9650cf6ad645";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView= (TextView)findViewById(R.id.textView_symptom);


        findViewById(R.id.button_nausea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptom = "nausea";
                Intent intent = new Intent(MainActivity.this, FoodTypeActivity.class);
                intent.putExtra("symptom", symptom);
                startActivity(intent);
            }
        });

        // Create new instance of HODClient that uses the api key and callback context.
        hodClient = new HODClient(HODClient.apiKey, this);
//        QueryAsyncTask queryAsyncTask = new QueryAsyncTask();
//        queryAsyncTask.execute(findSimilarQueryURL);
    }

    @Override
    public void onErrorOccurred(String errorMessage){
    // TODO: Handle an error.
    }


    // TODO: Connect Get Content api


    @Override
    public void requestCompletedWithContent(String response) {

    }
    @Override
    public void requestCompletedWithJobID(String response){ }

//    private class QueryAsyncTask extends AsyncTask<String, Void, Void> {
//        @Override
//        protected Void doInBackground(String... myUrl) {
//
//            String contentAsString = "";
//            InputStream inputStream = null;
//
//            try {
//                URL url = new URL(myUrl[0]);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//
//                // Starts the query
//                conn.connect();
//                inputStream = conn.getInputStream();
//
//                // Converts the InputStream into a string
//                contentAsString = getInputStream(inputStream);
//
//            } catch (Throwable thr) {
//                thr.printStackTrace();
//            }
//
//            try {
//                // Get the data inside the JSON object, and the data inside the object's array.
//                JSONObject dataObject = new JSONObject(contentAsString); // Could take no params, or could take the string you want to use.
//                JSONArray documentsJsonArray = dataObject.getJSONArray("documents");
//
//                // For every object in the item array, add the name to the ArrayList.
//                for (int i = 0; i < documentsJsonArray.length(); i++) {
//                    JSONObject object = documentsJsonArray.optJSONObject(i);
//                    reference = object.optString("reference");
//                    title = object.optString("title");
//                    summary = object.optString("summary");
//
//                    Log.d("QUERY_ASYNC_TASK", "Title: "+title+", Summary: "+summary+", Reference: "+reference);
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } finally {
//                if (inputStream != null) {
//                    try {
//                        inputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Log.d("OnPOST", "Loading.");
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            Log.d("OnPOST", "Done loading.");
//        }
//    }
//
//    public String getInputStream(InputStream stream) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
//        String read;
//
//        while((read = br.readLine()) != null) {
//            sb.append(read);
//        }
//
//        br.close();
//        return sb.toString();
//    }

}
