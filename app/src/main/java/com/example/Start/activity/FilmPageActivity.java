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
import android.widget.RatingBar;
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
    public static int previousTab = -1;
    private RatingBar fStars;

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
    private TextView fRating;
    private TextView fNumRating;
    private int currentStars = 0;


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

        fStars = (RatingBar) findViewById(R.id.fStars);
        fStars.setStepSize((int) 1.0);
        fStars.setMax(10);

        fStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int rating = (int) v;
                if(rating == currentStars) return;
                updateRatingForFilm((int) v);
                NetworkUtil.setRating(rating, MainTabActivity.user, (String) map.get("pk"));
                System.out.println();
            }
        });

        fRating = (TextView) findViewById(R.id.fRating);
        fNumRating = (TextView) findViewById(R.id.fNumRating);
    }

    private void updateRatingForFilm(int value) {
        int i = Integer.parseInt(fNumRating.getText().toString());
        double mid = Double.parseDouble(fRating.getText().toString());
        int sum = (int) (i * mid);
        int rating = currentStars;
        sum = sum - rating + value;
        i = rating == 0 ? i : (i - 1);
        float newRating = sum / (i+1);
        currentStars = value;
        fNumRating.setText(new Integer(i+1).toString());
        fRating.setText(new Float(newRating).toString());
    }

    private String cleanName(String name) {
        return name.split("/")[0];
    }

    private void fillInfo(Map<String, String> filmByPk) {

        if (filmByPk.containsKey("name_rus"))
            twRusName.setText(cleanName(filmByPk.get("name_rus")));
        if (filmByPk.containsKey("name")) twEngName.setText(cleanName(filmByPk.get("name")));
        if (filmByPk.containsKey("year")) twYear.setText(filmByPk.get("year"));
        if (filmByPk.containsKey("est_num")) fNumRating.setText(filmByPk.get("est_num"));
        if (filmByPk.containsKey("est_mid")) fRating.setText(filmByPk.get("est_mid"));
        if (filmByPk.containsKey("delay")) twTime.setText(filmByPk.get("delay"));
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
        if (map.isEmpty()) return;
        fillActivity();
    }

    @Override
    public void onBackPressed() {
        if (previousTab != -1) {
            int up = previousTab / 10;
            int tab = previousTab % 10;
            if (up == 1) {
                MainTabActivity.tabs.setCurrentTab(0);
                RibbonTabActivity.tabs.setCurrentTab(tab);
            } else {
                MainTabActivity.tabs.setCurrentTab(tab);
            }
            previousTab = -1;
        }
    }

    private void fillActivity() {
        final String pk = (String) map.get("pk");
        Map<String, String> filmByPk = NetworkUtil.getFilmByPk(pk, MainTabActivity.user);
        if (filmByPk.containsKey("userRating")) {
            fStars.setRating(Float.parseFloat(filmByPk.get("userRating")));
            currentStars = Integer.parseInt(filmByPk.get("userRating"));
        }
        fillInfo(filmByPk);

        Bitmap poster1 = NetworkUtil.getImage(filmByPk.get("poster"), pk);
        fPoster.setImageBitmap(poster1);

    }
}
