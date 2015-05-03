package com.example.Start.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.Start.R;

public class EstimateActivity extends Activity {
//    public TextView textView;
    public static String TAG = "myLog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "A2: onCreate()");
        setContentView(R.layout.estimate_row);
//        textView = (TextView) findViewById(R.id.textView);
//        textView.setText("A2 : oncreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "A2: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        textView.setText("A2 onRESUME");
        Log.d(TAG, "A2: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "A2: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "A2: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "A2: onDestroy()");
    }
}
