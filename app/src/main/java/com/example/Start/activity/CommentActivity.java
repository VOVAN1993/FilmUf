package com.example.Start.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.Start.R;

public class CommentActivity extends Activity {

    public static String TAG = "myLog";
//    public TextView textView;
//    public int a1 = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        a1++;
        Log.d(TAG, "A1: onCreate()");
        setContentView(R.layout.comment_row);

//        textView = (TextView) findViewById(R.id.textView);
//        textView.setText("A1 : oncreate" + a1);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "A1: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        a1++;
//        textView.setText(textView.getText() + "a");

//        Log.d(TAG, "A1: onResume()" + a1);
        Log.d(TAG, "A1: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "A1: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "A1: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "A1: onDestroy()");
    }
}
