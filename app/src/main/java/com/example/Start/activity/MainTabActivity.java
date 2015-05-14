package com.example.Start.activity;

import android.annotation.TargetApi;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.db.DBHelper;

public class MainTabActivity extends TabActivity {
    /** Called when the activity is first created. */
    public static TabHost tabs;
    public static DBHelper dbHelper;

    public static Bitmap emptyFilmPoster;
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_activity);
        Log.d("myLog", "MyTab : oncreate");
        emptyFilmPoster = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.blank_wanted_poster);
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        int clearCount = db.delete("tbl_image", null, null);
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
        spec.setContent(new Intent().setClass(this, ExpActivity.class));
        spec.setIndicator("", res.getDrawable(R.drawable.glass32));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag4");
        spec.setContent(new Intent().setClass(this, FilmPageActivity.class));
        spec.setIndicator("", res.getDrawable(R.drawable.house32));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag5");
        spec.setContent(new Intent().setClass(this, ListFilmsActivity.class));
        spec.setIndicator("", res.getDrawable(R.drawable.house32));
        tabs.addTab(spec);
        tabs.getTabWidget().getChildAt(4).setVisibility(View.GONE);

        spec = tabs.newTabSpec("tag5");
        spec.setContent(new Intent().setClass(this, FilmPageActivity.class));
        spec.setIndicator("", res.getDrawable(R.drawable.house32));
        tabs.addTab(spec);
        tabs.getTabWidget().getChildAt(5).setVisibility(View.GONE);

        tabs.setCurrentTab(2);

        setContentView(tabs);

        TabWidget widget = tabs.getTabWidget();
        for(int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);
            Drawable drawable = getResources().getDrawable(R.drawable.tab_indicator_ab_example);
            drawable.setAlpha(110);
            v.setBackground(drawable);
//            v.setBackgroundResource(R.drawable.tab_indicator_ab_example);
        }
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
