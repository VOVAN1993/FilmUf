package com.example.Start.activity;

import android.app.Activity;
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
import com.example.Start.adapter.CommentAdapter;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.Comment;
import com.example.Start.util.User;
import com.example.Start.util.request.Request;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CommentActivity extends Activity {

    private String name = "vova";

    public static Map<String, Object> map = new TreeMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);

        Set<Comment> commentsByFriends = Request.getCommentsByFriends(new User(name));
        ArrayList<Map<String,String>> data = new ArrayList<>();

        for(Comment c : commentsByFriends){
            data.add(c.toMap());
        }


        String[] from = {Comment.COMMENT_ATTRIBUTE_COMMENT, Comment.COMMENT_ATTRIBUTE_DATE,
                Comment.COMMENT_ATTRIBUTE_FILM, Comment.COMMENT_ATTRIBUTE_YEAR, Comment.COMMENT_ATTRIBUTE_USER,
                Comment.COMMENT_ATTRIBUTE_PK,
                Comment.COMMENT_ATTRIBUTE_LIKES,Comment.COMMENT_ATTRIBUTE_DISLIKES};

        int[] to = {R.id.cTitleRus,R.id.cDate, R.id.cEngName, R.id.cYear, R.id.cUserName};

        CommentAdapter adapter = new CommentAdapter(Request.getAllLikeComment(new User("vova")), Request.getAllDislikeComment(new User("vova")),
                this, data,R.layout.comment_row,from,to);
        ListView lv = ((ListView) findViewById(R.id.lvSimple));
        lv.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(BasicUtil.LOG_TAG,"CommentActivity onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(BasicUtil.LOG_TAG,"CommentActivity onReStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(BasicUtil.LOG_TAG,"CommentActivity onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(BasicUtil.LOG_TAG,"CommentActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(BasicUtil.LOG_TAG,"CommentActivity onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(BasicUtil.LOG_TAG, "CommentActivity onDestroy");

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cLike:

                ImageView iv = ((ImageView) view);
                if(iv.isSelected()){
                    //TODO: unlike
                }else{
                    LinearLayout parent = (LinearLayout) view.getParent().getParent().getParent();
                    like(parent);
                }
                break;
            case R.id.cDislike:
                ImageView iv2 = ((ImageView) view);
                if(iv2.isSelected()){
                    //TODO: undislike
                }else {
                    LinearLayout parent = (LinearLayout) view.getParent().getParent().getParent();
                    dislike(parent);
                }
                break;
            case R.id.cPoster:
                FilmPageActivity.map.clear();

                FilmPageActivity.map.put("name", Comment.COMMENT_ATTRIBUTE_FILM);
//                Log.d(BasicUtil.LOG_TAG, engName.toString());

                MainTabActivity.tabs.setCurrentTab(5);
                break;
        }
    }

    private void like(LinearLayout view){
        ImageView ivLike = (ImageView) view.findViewById(R.id.cLike);
        updateLikeNum(1,view);
        ImageView dislike = (ImageView)view.findViewById(R.id.cDislike);
        if(dislike.isSelected()) updateDisLikeNum(-1, view);
        dislike.setSelected(false);
        ivLike.setSelected(true);
        TextView invisibleTV = ((TextView) view.findViewById(R.id.invisibleTV));
        Request.likeComment(invisibleTV.getText().toString(), new User(name));
    }

    private void updateLikeNum(int diff, LinearLayout view){
        TextView tvLike = (TextView) view.findViewById(R.id.cLikeNum);
        int i = tvLike.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvLike.getText().toString());
        tvLike.setText(new Integer(i+diff).toString());
    }

    private void updateDisLikeNum(int diff, LinearLayout view){
        TextView tvLike = (TextView) view.findViewById(R.id.cDislikeNum);
        int i = tvLike.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvLike.getText().toString());
        tvLike.setText(new Integer(i+diff).toString());
    }

    private void dislike(LinearLayout view){
        ImageView ivDisLike = (ImageView) view.findViewById(R.id.cDislike);
        updateDisLikeNum(1,view);
        ImageView like = (ImageView)view.findViewById(R.id.cLike);

        if(like.isSelected()) updateLikeNum(-1, view);
        like.setSelected(false);
        ivDisLike.setSelected(true);
        TextView invisibleTV = ((TextView) view.findViewById(R.id.invisibleTV));
        Request.dislikeComment(invisibleTV.getText().toString(), new User(name));
    }
}
