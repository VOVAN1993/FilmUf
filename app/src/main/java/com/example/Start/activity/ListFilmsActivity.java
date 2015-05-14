package com.example.Start.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.adapter.LazyAdapter;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.Film;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ListFilmsActivity extends Activity {
    public static final String KEY_NAME = "name";
    public static final String KEY_DIRECTOR = "artist";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_THUMB_URL = "thumb_url";

    public static Map<String,Object> map = new TreeMap<>();
    ListView list;
    LazyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_films_activity);

//        Intent intent = getIntent();
        //if null -> not found + exception
//        ArrayList<Map<String, String>> extra = (ArrayList<Map<String, String>>) map.get("map");
//        if(intent.getStringExtra("Result").equals("Success")) {
//             extra = (ArrayList<Map<String, String>>)intent.getSerializableExtra("map");
//            System.out.println("asd");
//
//        }else{
//            Log.d(BasicUtil.LOG_TAG, "Finish ListFilmsActivity with bad code");
//            intent.putExtra("result", "Bad");
//            setResult(RESULT_CANCELED);
//            finish();
//        }
//
        list = (ListView) findViewById(R.id.list);
// Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Map<String, String> film = ((ArrayList<Map<String, String>>) map.get("map"))

                        .get(position);
                Log.d("my", "click");
                FilmPageActivity.map.clear();

                FilmPageActivity.map.put("pk", film.get("pk"));
                ImageView imgview = ((ImageView) findViewById(R.id.list_image));
                Drawable drawable = imgview.getDrawable();
                FilmPageActivity.map.put("poster", drawable);
                FilmPageActivity.previousTab = 24;
                MainTabActivity.tabs.setCurrentTab(5);
            }
        });
        // Getting adapter by passing xml data ArrayList
//        adapter = new LazyAdapter(this, extra);
//        list.setAdapter(adapter);
//
//
//        // Click event for single list row
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Log.d("my", "click");
//            }
//        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        fillActivity();
    }

    public void fillActivity(){
        final ArrayList<Map<String, String>> extra = (ArrayList<Map<String, String>>) map.get("map");

        // Getting adapter by passing xml data ArrayList
        adapter = new LazyAdapter(this, extra);
        list.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
    }
}
