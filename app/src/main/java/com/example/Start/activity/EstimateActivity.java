package com.example.Start.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.adapter.EstimateAdapter;
import com.example.Start.util.Estimate;
import com.example.Start.util.User;
import com.example.Start.util.request.Request;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class EstimateActivity extends Activity {
    public static String TAG = "myLog";
    private String name = "vova";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estimate_activity);

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.esPoster:
                FilmPageActivity.map.clear();

                RelativeLayout parent = ((RelativeLayout) view.getParent());
                TextView tw = (TextView) parent.findViewById(R.id.esInvisiblePK);
                String pk = tw.getText().toString();
                FilmPageActivity.map.put("pk", pk);
                Drawable drawable = ((ImageView) view).getDrawable();
                FilmPageActivity.map.put("poster", drawable);
                FilmPageActivity.previousTab=11;
                MainTabActivity.tabs.setCurrentTab(5);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Set<Estimate> estimatesByFriends = Request.getEstimatesByFriends(new User(MainTabActivity.user));
        ArrayList<Map<String,Object>> data = new ArrayList<>();

        for(Estimate e: estimatesByFriends){
            data.add(e.toMapWithPoster());
        }

        String[] from = {Estimate.ESTIMATE_ATTRIBUTE_FILM_POSTER,
                Estimate.ESTIMATE_ATTRIBUTE_ESTIMATE,
                Estimate.ESTIMATE_ATTRIBUTE_FILM,
                Estimate.ESTIMATE_ATTRIBUTE_USER,
                Estimate.ESTIMATE_ATTRIBUTE_DATE,
                Estimate.ESTIMATE_ATTRIBUTE_FILM_PK};

        int[] to = {R.id.esPoster, R.id.esUserEstimate, R.id.esRusName, R.id.esUserName, R.id.esDate, R.id.esInvisiblePK};

        SimpleAdapter adapter = new EstimateAdapter(this, data, R.layout.estimate_row, from, to, "estimate");
        ListView lv = ((ListView) findViewById(R.id.lvSimple));
        lv.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
    }
}
