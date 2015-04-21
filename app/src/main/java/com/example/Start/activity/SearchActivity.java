package com.example.Start.activity;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;

import com.example.Start.R;
import com.example.Start.adapter.AdapterHelper;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.NetworkUtil;
import com.example.Start.util.RangeSeekBar;
import com.example.Start.util.asyncTasks.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends Activity {


    ExpandableListView elvMain;
    AdapterHelper ah;
    SimpleExpandableListAdapter adapter;
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        // создаем адаптер
        ah = new AdapterHelper(this);
        adapter = ah.getAdapter();

//        linearGroup
        elvMain = (ExpandableListView) findViewById(R.id.elvMain);
        elvMain.setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE);
        elvMain.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                CheckedTextView view1 = (CheckedTextView) view;
                view1.setChecked(true);
                return false;
            }
        });
        elvMain.setAdapter(adapter);

    }

    private ArrayList<Map<String, String>> search(Map<String, String> map){
        return NetworkUtil.requestToMyServer("http://109.234.36.127:8000/dasha/getFilmByCountry/USA");
    }

    private String createURI(Map<String,String> map){
        String addr = "http://109.234.36.127:8000/dasha/getFilm/127%20hours";
        return addr;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.search){
            ArrayList<Map<String, String>> search = search(null);
            Intent intent = new Intent(this, ListFilmsActivity.class);
            if(search.size()>0) {
                intent.putExtra("map", search);
                intent.putExtra("Result", "Success");
            }else{
                intent.putExtra("Result", "Bad");
            }
            startActivityForResult(intent, 1);
        }
        if(view.getId() == R.id.tmp){
            ViewGroup layout2 = ((ViewGroup) findViewById(R.id.linearGroupChild));
            RangeSeekBar<Integer> bar = new RangeSeekBar<Integer>(this);
            layout2.addView(bar,0);
            Log.d(BasicUtil.LOG_TAG, " asdasdasd!!!!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || !data.getStringExtra("result").equals("Success")) {return;}
        Log.d(BasicUtil.LOG_TAG, "Show " + data.getStringExtra("film") + "film");
    }
}