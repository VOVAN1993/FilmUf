package com.example.Start.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.util.Comment;
import com.example.Start.util.User;
import com.example.Start.util.request.Request;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class CommentActivity extends Activity {

    public static String TAG = "myLog";
    private String name = "vova";
//    public TextView textView;
//    public int a1 = 0;

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
                Comment.COMMENT_ATTRIBUTE_FILM,Comment.COMMENT_ATTRIBUTE_USER};

        int[] to = {R.id.twTitleRus,R.id.cDate, R.id.twEngName, R.id.cUserName};

        SimpleAdapter adapter = new SimpleAdapter(this, data,R.layout.comment_row,from,to);
        ListView lv = ((ListView) findViewById(R.id.lvSimple));
        lv.setAdapter(adapter);
    }

}
