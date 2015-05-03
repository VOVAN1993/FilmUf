package com.example.Start.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.example.Start.R;

/**
 * Created by Даша on 23.04.2015.
 */
public class RibbonTabActivity extends TabActivity {
    /** Called when the activity is first created. */
    public static TabHost tabs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ribbon_tab_activity);
        Log.d("myLog", "MyTab : oncreate");
        Resources res = getResources();

        tabs = (TabHost) findViewById(android.R.id.tabhost);

        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("ribtag1");
        spec.setContent(new Intent().setClass(this, CommentActivity.class));
        spec.setIndicator("комментарии");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("ribtag2");
        spec.setContent(new Intent().setClass(this, EstimateActivity.class));
        spec.setIndicator("оценки");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myLog", "MyRibTabAct: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myLog", "MyRibTabAct: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myLog", "MyRibTabAct: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myLog", "MyRibTabAct: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myLog", "MyRibTabAct: onDestroy()");
    }
}
