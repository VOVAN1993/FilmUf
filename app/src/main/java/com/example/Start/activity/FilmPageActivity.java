package com.example.Start.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.Film;
import com.example.Start.util.NetworkUtil;
import com.example.Start.util.asyncTasks.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class FilmPageActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    public static Map<String, Object> map = new TreeMap<>();

    private TextView twRusName;
    private TextView twEngName;
    private TextView twYear;
    private TextView twTime;
    private TextView twCountry;
    private TextView twGenres;
    private TextView twDirector;
    private TextView twActors;
    private TextView twTitleRus;
    private ImageView fPoster;

    private void print(String s) {
        twEngName.setText(s);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_page_activity);


        twRusName = (TextView) findViewById(R.id.twRusName);
        twEngName = (TextView) findViewById(R.id.twEngName);
        twYear = (TextView) findViewById(R.id.twYear);
        twTime = (TextView) findViewById(R.id.twTime);
        twCountry = (TextView) findViewById(R.id.twCountry);
        twGenres = (TextView) findViewById(R.id.twGenres);
        twDirector = (TextView) findViewById(R.id.twDirector);
        twActors = (TextView) findViewById(R.id.twActors);
        twTitleRus = (TextView) findViewById(R.id.twTitleRus);
        fPoster = (ImageView) findViewById(R.id.fPoster);
//        fillActivity();

//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                MyAsyncTask myAsyncTask = new MyAsyncTask();
//                myAsyncTask.execute("http://109.234.36.127:8000/dasha/getFilm/...Pervaya%20lyubov");
//                try {
//                    String response = myAsyncTask.get();
//                    JSONObject jsonObject = new JSONArray(response).getJSONObject(0).getJSONObject("fields");
//
//
//                    twRusName.setText(jsonObject.get("name_rus").toString());
//                    twEngName.setText(jsonObject.get("name").toString());
//                    twYear.setText(jsonObject.get("year").toString());
//                    twTime.setText(jsonObject.get("time").toString());
//
//                    JSONArray countries = jsonObject.getJSONArray("country");
//                    String filmCountries = "";
//                    for (int i = 0; i < countries.length(); i++) {
//                        if(i == countries.length() - 1)
//                            filmCountries += countries.get(i);
//                        else filmCountries += countries.get(i) + " | ";
//                    }
//                    twCountry.setText(filmCountries);
//
//                    JSONArray genres = jsonObject.getJSONArray("genres");
//                    String filmGenres = "";
//                    for (int i = 0; i < genres.length(); i++) {
//                        if(i == genres.length() - 1)
//                        filmGenres += genres.get(i);
//                        else filmGenres += genres.get(i) + " | ";
//                    }
//                    twGenres.setText(filmGenres);
//
//                    twTitleRus.setText(jsonObject.get("title_rus").toString());
//
//                    JSONArray directors = jsonObject.getJSONArray("directors");
//                    String filmDirectors = "";
//                    for (int i = 0; i < directors.length(); i++) {
//                        if(i == directors.length() - 1)
//                            filmDirectors += directors.get(i);
//                        else filmDirectors += directors.get(i) + " , ";
//                    }
//                    twDirector.setText(filmDirectors);
//
//                    JSONArray actors = jsonObject.getJSONArray("actors");
//                    String filmActors = "";
//                    for (int i = 0; i < actors.length(); i++) {
//                        if(i == actors.length() - 1)
//                            filmActors += actors.get(i);
//                        else filmActors += actors.get(i) + " , ";
//                    }
//                    twActors.setText(filmActors);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    private void fillInfo(Map<String, String> filmByPk) {
        System.out.println();


//        twRusName = (TextView) findViewById(R.id.twRusName);
//        twEngName = (TextView) findViewById(R.id.twEngName);
//        twYear = (TextView) findViewById(R.id.twYear);
//        twTime = (TextView) findViewById(R.id.twTime);
//        twCountry = (TextView) findViewById(R.id.twCountry);
//        twGenres = (TextView) findViewById(R.id.twGenres);
//        twDirector = (TextView) findViewById(R.id.twDirector);
//        twActors = (TextView) findViewById(R.id.twActors);
//        twTitleRus = (TextView) findViewById(R.id.twTitleRus);
//        fPoster = (ImageView) findViewById(R.id.fPoster);
        if (filmByPk.containsKey("name_rus")) twRusName.setText(filmByPk.get("name_rus"));
        if (filmByPk.containsKey("name")) twEngName.setText(filmByPk.get("name"));
        if (filmByPk.containsKey("year")) twYear.setText(filmByPk.get("year"));
        if (filmByPk.containsKey("time")) twTime.setText(filmByPk.get("time"));
        if (filmByPk.containsKey("country")) twCountry.setText(filmByPk.get("country"));
        if (filmByPk.containsKey("genres")) twGenres.setText(filmByPk.get("genres"));
        if (filmByPk.containsKey("directors")) twDirector.setText(filmByPk.get("directors"));
        if (filmByPk.containsKey("actors")) twActors.setText(filmByPk.get("actors"));
        if (filmByPk.containsKey("title_rus")) twTitleRus.setText(filmByPk.get("title_rus"));
        else if (filmByPk.containsKey("title")) twTitleRus.setText(filmByPk.get("title"));
        if (filmByPk.containsKey("directors")) twDirector.setText(filmByPk.get("directors"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillActivity();
    }

    private void fillActivity() {
        final String pk = (String) map.get("pk");
        Drawable poster = (Drawable) map.get("poster");
        Map<String, String> filmByPk = NetworkUtil.getFilmByPk(pk);
        fillInfo(filmByPk);

        fPoster.setImageDrawable(poster);


    }
}
