package com.example.Start;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private TextView twRusName;
    private TextView twEngName;
    private TextView twYear;
    private TextView twTime;
    private TextView twDirector;
    private TextView twTitleRus;

    private void print(String s) {
        twEngName.setText(s);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        twRusName = (TextView) findViewById(R.id.twRusName);
        twEngName = (TextView) findViewById(R.id.twEngName);
        twYear = (TextView) findViewById(R.id.twYear);
        twTime = (TextView) findViewById(R.id.twTime);
        twDirector = (TextView) findViewById(R.id.twDirector);
        twTitleRus = (TextView) findViewById(R.id.twTitleRus);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute("http://109.234.36.127:8000/dasha/getFilm/54");
                try {
                    String response = myAsyncTask.get();
                    JSONObject jsonObject = new JSONArray(response).getJSONObject(0).getJSONObject("fields");


//                    String rus_name = jsonObject.get("name_rus").toString();
                    twRusName.setText(jsonObject.get("name_rus").toString());
                    twEngName.setText(jsonObject.get("name").toString());
                    twYear.setText(jsonObject.get("year").toString());
                    twTime.setText(jsonObject.get("time").toString());
                    twDirector.setText(jsonObject.get("directors").toString());
                    twTitleRus.setText(jsonObject.get("title_rus").toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
