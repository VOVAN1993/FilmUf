package com.example.Start.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.adapter.CommentAdapter;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.Comment;
import com.example.Start.util.Film;
import com.example.Start.util.NetworkUtil;
import com.example.Start.util.User;
import com.example.Start.util.asyncTasks.MyAsyncTask;
import com.example.Start.util.request.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
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
    ArrayList<Map<String, Object>> data = null;
    String pk;

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
                if (rating == currentStars) return;
                updateRatingForFilm((int) v);
                NetworkUtil.setRating(rating, MainTabActivity.user, (String) map.get("pk"));
                System.out.println();
            }
        });

        fRating = (TextView) findViewById(R.id.fRating);
        fNumRating = (TextView) findViewById(R.id.fNumRating);


        //-----------comments---------------


    }

    private void updateRatingForFilm(int value) {
        int i = Integer.parseInt(fNumRating.getText().toString());
        double mid = Double.parseDouble(fRating.getText().toString());
        int sum = (int) (i * mid);
        int rating = currentStars;
        sum = sum - rating + value;
        i = rating == 0 ? i : (i - 1);
        float newRating = sum / (i + 1);
        currentStars = value;
        fNumRating.setText(new Integer(i + 1).toString());
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

    public void fillComment(){

        Set<Comment> comments = Request.getAllcommentsForFilm((String) map.get("pk"));

        ArrayList<Map<String, Object>> data = new ArrayList<>();

        for(Comment c:comments){
            data.add(c.toMap());
        }

        String[] from = {
                Comment.COMMENT_ATTRIBUTE_PK,
                Comment.COMMENT_ATTRIBUTE_COMMENT,
                Comment.COMMENT_ATTRIBUTE_DATE,
                Comment.COMMENT_ATTRIBUTE_USER,
                Comment.COMMENT_ATTRIBUTE_LIKES,
                Comment.COMMENT_ATTRIBUTE_DISLIKES
        };

        int[] to = {R.id.fInvisibleTVCommentPK, R.id.cTitleRus, R.id.cDate, R.id.cUserName, R.id.fLikeNum, R.id.fDislikeNum};

        ArrayList<Comment> likes = Request.getAllLikeComment(new User(MainTabActivity.user));
        ArrayList<Comment> dislikes = Request.getAllDislikeComment(new User(MainTabActivity.user));
        CommentAdapter sAdapter = new CommentAdapter(likes, dislikes,this, data, R.layout.film_comment_row,
                from, to, "film");
        ListView lv = ((ListView) findViewById(R.id.fCommentList));
        lv.setAdapter(sAdapter);
        setListViewHeightBasedOnChildren(lv);
//        sAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (map.isEmpty()) return;
        fillActivity();
        fillComment();
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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.cAvatar:
                RelativeLayout parent1 = (RelativeLayout) view.getParent();
                TextView userName = (TextView) parent1.findViewById(R.id.cUserName);
                String strName = userName.getText().toString();
                UserPageActivity.map.clear();
                UserPageActivity.map.put("user", strName);

                UserPageActivity.previousTab=5;
                MainTabActivity.tabs.setCurrentTab(7);
                break;
            case R.id.cLike:

                ImageView iv = ((ImageView) view);
                if(iv.isSelected()){
                    //TODO: unlike
                }else{
                    RelativeLayout parent = (RelativeLayout) view.getParent();
                    like(parent);
                }
                break;
            case R.id.cDislike:
                ImageView iv2 = ((ImageView) view);
                if(iv2.isSelected()){
                    //TODO: undislike
                }else {
                    RelativeLayout parent = (RelativeLayout) view.getParent();
                    dislike(parent);
                }
                break;
            case R.id.fRatingButton:
                EditText viewById = (EditText) findViewById(R.id.fEditText);
                String s = viewById.getText().toString();
                viewById.setText("");
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewById.getWindowToken(), 0);
                String filmPk = (String) map.get("pk");
                String user = MainTabActivity.user;
                NetworkUtil.addComment(s,user,filmPk);
                onBackPressed();
                break;
        }
    }


    private void like(RelativeLayout view){
        ImageView ivLike = (ImageView) view.findViewById(R.id.cLike);
        updateLikeNum(1,view);
        ImageView dislike = (ImageView)view.findViewById(R.id.cDislike);
        if(dislike.isSelected()) updateDisLikeNum(-1, view);
        dislike.setSelected(false);
        ivLike.setSelected(true);
        TextView invisibleTV = ((TextView) view.findViewById(R.id.fInvisibleTVCommentPK));
        Request.likeComment(invisibleTV.getText().toString(), new User(MainTabActivity.user));
    }

    private void updateLikeNum(int diff, RelativeLayout view){
        TextView tvLike = (TextView) view.findViewById(R.id.fLikeNum);
        int i = tvLike.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvLike.getText().toString());
        tvLike.setText(new Integer(i + diff).toString());
    }

    private void updateDisLikeNum(int diff, RelativeLayout view){
        TextView tvLike = (TextView) view.findViewById(R.id.fDislikeNum);
        int i = tvLike.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvLike.getText().toString());
        tvLike.setText(new Integer(i + diff).toString());
    }

    private void dislike(RelativeLayout view){
        ImageView ivDisLike = (ImageView) view.findViewById(R.id.cDislike);
        updateDisLikeNum(1,view);
        ImageView like = (ImageView)view.findViewById(R.id.cLike);

        if(like.isSelected()) updateLikeNum(-1, view);
        like.setSelected(false);
        ivDisLike.setSelected(true);
        TextView invisibleTV = ((TextView) view.findViewById(R.id.fInvisibleTVCommentPK));
        Request.dislikeComment(invisibleTV.getText().toString(), new User(MainTabActivity.user));
    }
}
