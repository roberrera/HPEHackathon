package com.roberterrera.foodpoisoning;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

    String testQueryURL = "https://api.havenondemand.com/1/api/sync/findrelatedconcepts/v1?text=chipotle%2C+food+poisoning&indexes=news_eng&min_score=75&sample_size=10&apikey=50d036f8-e46f-4242-aac6-9650cf6ad645";

    // TODO: Add member variables for Views.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // For testing purposes:
        userQuery = "meat%2C+nausea%2C+new+york";

        // TODO: Connect views to xml files.

        // Create new instance of HODClient that uses the api key and callback context.
        hodClient = new HODClient(HODClient.apiKey, this);
        useHODClient();
    }

    @Override
    public void onErrorOccurred(String errorMessage){
    // TODO: Handle an error.
    }


    // Leverages the "Find related concepts" API to search the specified news sites below for concepts related to the user's text query.
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
        params.put("query", testQueryURL);
        params.put("unique_entities", "true");

        hodClient.GetRequest(params, hodApp, HODClient.REQ_MODE.SYNC);
    }

    void PostRequest(Map<String,Object> params, String hodApp, HODClient.REQ_MODE mode){

    }


    // TODO: Connect Get Content api

    @Override
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
                    //print sentiment result
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

    @Override
    public void requestCompletedWithJobID(String response){ }

}
