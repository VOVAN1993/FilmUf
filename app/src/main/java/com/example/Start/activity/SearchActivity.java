package com.example.Start.activity;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;

import com.example.Start.R;
import com.example.Start.adapter.AdapterHelper;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.NetworkUtil;
import com.example.Start.util.asyncTasks.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends Activity {


    ExpandableListView elvMain;
    AdapterHelper ah;
    SimpleExpandableListAdapter adapter;
    ImageView image;
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        // создаем адаптер
        ah = new AdapterHelper(this);
        adapter = ah.getAdapter();

        image = (ImageView) findViewById(R.id.iv);
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

    private Map<String, String> search(Map<String, String> map){
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        Map<String,String> resultForIntent = new TreeMap<>();
        myAsyncTask.execute(createURI(map));
        try {
            String response = myAsyncTask.get();
            if(response==null) throw new ExecutionException(new NetworkErrorException("NetworkException"));
            JSONObject jsonObject = new JSONArray(response).getJSONObject(0).getJSONObject("fields");
            BasicUtil.putIfNotNull(resultForIntent, "name_rus", !jsonObject.isNull("name_rus") ? jsonObject.get("name_rus").toString() : null);
            BasicUtil.putIfNotNull(resultForIntent, "name", !jsonObject.isNull("name") ? jsonObject.get("name").toString() : null);

            if(resultForIntent.size()>0){
                resultForIntent.put("Result","Success");
            }
            return resultForIntent;
        } catch (InterruptedException |ExecutionException | JSONException e) {
            Log.d(BasicUtil.LOG_TAG, e.toString());
            resultForIntent.clear();
            resultForIntent.put("Result","Error");
        }
        return resultForIntent;
    }

    private String createURI(Map<String,String> map){
        String addr = "http://109.234.36.127:8000/dasha/getFilm/127%20hours";
        return addr;
    }

    public void onClick(View view) {
        image.setImageBitmap(NetworkUtil.getImage("http://ia.media-imdb.com/images/M/MV5BMTc2NjMzOTE3Ml5BMl5BanBnXkFtZTcwMDE0OTc5Mw@@._V1_SX300.jpg",this));
//        if (view.getId() == R.id.search){
//            Map<String, String> search = search(null);
//            Intent intent = new Intent(this, ListFilmsActivity.class);
//            for(Map.Entry<String, String> entry:search.entrySet()){
//                intent.putExtra(entry.getKey(),entry.getValue());
//            }
//            startActivityForResult(intent, 1);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || !data.getStringExtra("result").equals("Success")) {return;}
        Log.d(BasicUtil.LOG_TAG, "Show " + data.getStringExtra("film") + "film");
    }
}