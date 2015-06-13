package com.example.Start.util.request;


import com.example.Start.util.Comment;
import com.example.Start.util.Estimate;
import com.example.Start.util.Film;
import com.example.Start.util.User;
import com.example.Start.util.asyncTasks.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

public class Request {
    public static Set<Comment> getAllcommentsForFilm(String pk) {
        String str = "http://109.234.36.127:8000/dasha/getAllCommentsForFilm/?film=" + pk;
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(str);
        try {
            String response = myAsyncTask.get();
            Set<Comment> ret = parseJSONComment(response);
            return ret;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Set<Comment> parseJSONComment(String json) throws JSONException {
        SortedSet<Comment> ret = new TreeSet<Comment>(new Comparator() {
            @Override
            public int compare(Object o, Object o2) {
                return ((Comment) o).date.after(((Comment) o2).date) ? 1 : -1;
            }
        });
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Film film = new Film(obj.getInt("film_id"), obj.getString("film"));
            film.poster = obj.getString("film_poster");
            film.name_rus = obj.getString("film_rus");
            Comment c = new Comment(obj.getString("comment"),
                    new User(obj.getString("users")),
                    film,
//                            new Integer(obj.getInt("year")).toString(),
                    "2012",
                    new Date(obj.getLong("timestamp")),
                    obj.getString("pk"),
                    obj.getString("cl"),
                    obj.getString("cd"));
            ret.add(c);
        }
        return ret;
    }

    public static Set<Comment> getCommentsByFriends(User user) {

        String str = "http://109.234.36.127:8000/dasha/getAllCommentsByFriends?user=" + user.name;
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(str);
        try {
            String response = myAsyncTask.get();
            Set<Comment> ret = parseJSONComment(response);
            return ret;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            //TODO:?????? handle exceptions
            e.printStackTrace();
        }

        return null;
    }

    public static void likeComment(String pk, User user) {
        String str = "http://109.234.36.127:8000/dasha/likeComment?user=" + user.name +
                "&comment=" + pk;

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(str);
        try {
            String s = myAsyncTask.get();
            System.out.println();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void dislikeComment(String pk, User user) {
        String str = "http://109.234.36.127:8000/dasha/dislikeComment?user=" + user.name +
                "&comment=" + pk;

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(str);
        try {
            String s = myAsyncTask.get();
            System.out.println();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Comment> getAllDislikeComment(User user) {
        String str = "http://109.234.36.127:8000/dasha/getAllDisLikeComments?user=" + user.name;

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(str);
        try {
            return parseJSONForListComment(new JSONArray(myAsyncTask.get()));
        } catch (InterruptedException | ExecutionException | JSONException e) {
            return new ArrayList<>();
        }
    }

    public static ArrayList<Comment> getAllLikeComment(User user) {
        String str = "http://109.234.36.127:8000/dasha/getAllLikeComments?user=" + user.name;

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(str);
        try {

            return parseJSONForListComment(new JSONArray(myAsyncTask.get()));
        } catch (InterruptedException | ExecutionException | JSONException e) {
            return new ArrayList<>();
        }
    }

    private static ArrayList<Comment> parseJSONForListComment(JSONArray jsonArray) {
        ArrayList<Comment> ret = new ArrayList<>();
        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Film film = new Film(obj.getInt("film_id"), obj.getString("film"));
                film.poster = obj.getString("film_poster");
                film.name_rus = obj.getString("film_rus");
                Comment c = new Comment(obj.getString("comment"),
                        new User(obj.getString("users")),
                        film,
//                        new Integer(obj.getString("year")).toString(),
                        "2012",
                        new Date(obj.getLong("timestamp")),
                        obj.getString("pk"),
                        obj.getString("cl"),
                        obj.getString("cd"));
                ret.add(c);
            }
            return ret;
        } catch (JSONException e) {
            return new ArrayList<>();
        }
    }

    public static Set<Estimate> getEstimatesByFriends(User user) {
        SortedSet<Estimate> ret = new TreeSet<Estimate>(new Comparator() {
            @Override
            public int compare(Object o, Object o2) {
                return ((Estimate) o).date.after(((Estimate) o2).date) ? 1 : -1;
            }
        });
        String str = "http://109.234.36.127:8000/dasha/getAllRatingByFriends?user=" + user.name;
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(str);
        try {
            String response = myAsyncTask.get();
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray item = jsonArray.getJSONArray(i);
                for (int j = 0; j < item.length(); j++) {
                    JSONObject obj = item.getJSONObject(j);
                    Film film = new Film(obj.getInt("film_id"), obj.getString("film"));
                    film.poster = obj.getString("film_poster");
                    film.name_rus = obj.getString("film_rus");
                    film.year = obj.getString("film_year");
                    film.est_num = obj.getString("film_num");
                    film.est_mid = obj.getString("film_mid");
                    Estimate c = new Estimate(obj.getString("value"),
                            new User(obj.getString("users")),
                            film,
                            new Date(obj.getLong("timestamp")));
                    ret.add(c);
                }
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return ret;

    }
}
