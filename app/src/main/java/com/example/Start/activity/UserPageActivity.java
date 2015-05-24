package com.example.Start.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.util.NetworkUtil;

import java.util.Map;
import java.util.TreeMap;

public class UserPageActivity extends Activity {
    public static Map<String, Object> map = new TreeMap<>();
    public static int previousTab =0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!map.isEmpty()) {
            Map<String, String> user = NetworkUtil.getUserInfo((String) map.get("user"));
            TextView nameTV = (TextView) findViewById(R.id.uName);
            nameTV.setText(user.get("name"));
            TextView sexTV = (TextView) findViewById(R.id.uSex);
            sexTV.setText(user.get("sex").equalsIgnoreCase("M") ? "М" : "Ж");
            TextView ageTV = (TextView) findViewById(R.id.uAge);
            ageTV.setText(user.get("age"));
            ImageView plus1 = (ImageView) findViewById(R.id.uPlus1);
            if(user.get("you_have_friend").equalsIgnoreCase("true")){
                plus1.setSelected(true);
            }else{
                plus1.setSelected(false);
            }
            ImageView plus2 = (ImageView) findViewById(R.id.uPlus2);
            if(user.get("friend_have_you").equalsIgnoreCase("true")){
                plus2.setSelected(true);
            }else{
                plus2.setSelected(false);
            }
//                    uAvatar
            System.out.println();
        }
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

    public void onClick(View v){
        switch (v.getId()){
            case R.id.uPlus1:
                ImageView img = (ImageView) v;
                TextView nameTV = (TextView) findViewById(R.id.uName);
                if(MainTabActivity.user==null || MainTabActivity.user.equals("")) return;
                if(!img.isSelected()){
                    if(!nameTV.getText().toString().equals(MainTabActivity.user)) {
                        img.setClickable(false);
                        if(NetworkUtil.addFriend(MainTabActivity.user, nameTV.getText().toString())){
                            img.setSelected(true);
                        }
                        img.setClickable(true);
                    }
                }else{
                    img.setClickable(false);
                    if (NetworkUtil.deleteFriend(MainTabActivity.user, nameTV.getText().toString())) {
                        img.setSelected(false);
                    }
                    img.setClickable(true);
                }
                break;
        }
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        ImageView plus2 = (ImageView) findViewById(R.id.uPlus2);
//        ImageView plus1 = (ImageView) findViewById(R.id.uPlus1);
//        plus1.setSelected(false);
//        plus2.setSelected(false);
//    }
}
