package com.example.Start.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

    ListView list;
    LazyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_films_activity);

        Intent intent = getIntent();
        //if null -> not found + exception
        ArrayList<Map<String, String>> extra = new ArrayList<>();
        if(intent.getStringExtra("Result").equals("Success")) {
             extra = (ArrayList<Map<String, String>>)intent.getSerializableExtra("map");
            System.out.println("asd");

        }else{
            Log.d(BasicUtil.LOG_TAG, "Finish ListFilmsActivity with bad code");
            intent.putExtra("result", "Bad");
            setResult(RESULT_CANCELED);
            finish();
        }
//
        list = (ListView) findViewById(R.id.list);

        // Getting adapter by passing xml data ArrayList
        adapter = new LazyAdapter(this, extra);
        list.setAdapter(adapter);


        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("my", "click");
            }
        });
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.film_page_activity);
//
//        twRusName = (TextView) findViewById(R.id.twRusName);
//        twEngName = (TextView) findViewById(R.id.twEngName);
//
//        Intent intent = getIntent();
//        //if null -> not found + exception
//        if(intent.getStringExtra("Result").equals("Success")) {
//            twRusName.setText(intent.getStringExtra("name_rus"));
//            twEngName.setText(intent.getStringExtra("name"));
//        }else{
//            Log.d(BasicUtil.LOG_TAG, "Finish ListFilmsActivity with bad code");
//            intent.putExtra("result", "Bad");
//            setResult(RESULT_CANCELED);
//            finish();
//        }
//
//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("result", "Success");
//                intent.putExtra("film", "127 hours");
//                Log.d(BasicUtil.LOG_TAG, "Finish ListFilmsActivity with OK code");
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        });
//    }

}
