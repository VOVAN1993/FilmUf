package com.example.Start.util.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.Start.util.BasicUtil;
import com.example.Start.util.NetworkUtil;

import java.util.IllegalFormatCodePointException;
import java.util.concurrent.ExecutionException;

public class MyAsyncTask extends AsyncTask<String, Void, String> {

    protected String doInBackground(String... urls) {
        try {
            Log.d(BasicUtil.LOG_TAG,"Start");
            String url = urls[0];
            url = url.replace(" ","%20");
            String s = NetworkUtil.connectToServer(url);
            Log.d(BasicUtil.LOG_TAG, "End");
            return s;

        } catch (Exception e) {
            return null;
        }
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        //org.apache.http.conn.HttpHostConnectException: Connection to http://127.0.0.1:8000 refused
        // TODO: do something with the feed
    }
}

