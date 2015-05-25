package com.example.Start.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.Start.R;
import com.example.Start.util.NetworkUtil;

public class BookmarkActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boormark_activity);
    }


    @Override
    public void onBackPressed() {
    }
}
