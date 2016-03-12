package com.roberterrera.foodpoisoning;

import android.app.Activity;

import hod.api.hodclient.HODClient;
import hod.api.hodclient.IHODClientCallback;

/**
 * Created by Rob on 3/12/16.
 */
public class HODClass extends Activity implements IHODClientCallback {

    HODClient hodClient = new HODClient("50d036f8-e46f-4242-aac6-9650cf6ad645", this);

    @Override
    public void requestCompletedWithJobID(String response){ }

    @Override
    public void requestCompletedWithContent(String response){ }

    @Override
    public void onErrorOccurred(String errorMessage){ }
}