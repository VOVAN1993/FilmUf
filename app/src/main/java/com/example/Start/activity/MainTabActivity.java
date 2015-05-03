package com.example.Start.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.example.Start.R;

public class MainTabActivity extends TabActivity {
    /** Called when the activity is first created. */
    public static TabHost tabs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_activity);
        Log.d("myLog", "MyTab : oncreate");
        Resources res = getResources();

        tabs = (TabHost) findViewById(android.R.id.tabhost);

        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(new Intent().setClass(this, RibbonTabActivity.class));
        spec.setIndicator("", res.getDrawable(R.drawable.talk32));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(new Intent().setClass(this, LoginActivity.class));
        spec.setIndicator("", res.getDrawable(R.drawable.star32));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag3");
        spec.setContent(new Intent().setClass(this, SearchActivity.class));
        spec.setIndicator("", res.getDrawable(R.drawable.glass32));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag4");
        spec.setContent(new Intent().setClass(this, FilmPageActivity.class));
        spec.setIndicator("", res.getDrawable(R.drawable.house32));
        tabs.addTab(spec);

        tabs.setCurrentTab(3);

        setContentView(tabs);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myLog", "MyTabAct: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myLog", "MyTabAct: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myLog", "MyTabAct: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myLog", "MyTabAct: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myLog", "MyTabAct: onDestroy()");
    }
}
