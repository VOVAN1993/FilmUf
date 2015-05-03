package com.example.Start.util.request;


import com.example.Start.util.Comment;
import com.example.Start.util.Film;
import com.example.Start.util.User;
import com.example.Start.util.asyncTasks.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

public class Request {

    public static Set<Comment> getCommentsByFriends(User user) {
        SortedSet<Comment> ret = new TreeSet<Comment>(new Comparator() {
            @Override
            public int compare(Object o, Object o2) {
                return ((Comment) o).date.after(((Comment) o2).date) ? 1 : -1;
            }
        });
        String str = "http://109.234.36.127:8000/dasha/getAllCommentsByFriends?user=" + user.name;
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(str);
        try {
            String response = myAsyncTask.get();
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray item = jsonArray.getJSONArray(i);
                for (int j = 0; j < item.length(); j++) {
                    JSONObject obj = item.getJSONObject(j);
                    Comment c = new Comment(obj.getString("comment"),
                            new User(obj.getString("users")),
                            new Film(obj.getInt("film_id"), obj.getString("film")),
                            new Date(obj.getLong("timestamp")));
                    ret.add(c);
                }
            }
        } catch (InterruptedException |ExecutionException| JSONException e) {
            //TODO:?????? handle exceptions
            e.printStackTrace();
        }

        return ret;
    }
}
