package com.example.Start.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;

import com.example.Start.R;
import com.example.Start.activity.MainTabActivity;
import com.example.Start.util.asyncTasks.DownloadImageTask;
import com.example.Start.util.asyncTasks.MyAsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class NetworkUtil {

    public static ArrayList<Map<String, String>> requestToMyServer(String url) {
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(url);
        try {
            String s = myAsyncTask.get();
            ArrayList<Film> films = BasicUtil.jsonToFilm(s);
            return BasicUtil.filmsToMaps(films);
        } catch (ExecutionException | InterruptedException e) {
            Log.e(BasicUtil.LOG_TAG, "Error when requestToMyServer. " + e.toString());
            return null;
        }
    }

    public static boolean setRating(int value, String user, String pk) {
        String url = "http://109.234.36.127:8000/dasha/setRating?user=" + user + "&film=" + pk + "&value=" + value;
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(url);
        try {
            String s = myAsyncTask.get();
            return s.equals("OK");
        } catch (ExecutionException | InterruptedException e) {
            Log.e(BasicUtil.LOG_TAG, "Error when requestToMyServer. " + e.toString());
            return false;
        }
    }

    public static boolean addComment(String comment, String user, String pk) {
        String url = "http://109.234.36.127:8000/dasha/addComment?user=" + user + "&pk=" + pk + "&comment=" + comment.replace(" ","%20");
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(url);
        try {
            String s = myAsyncTask.get();
            return s.equals("OK");
        } catch (ExecutionException | InterruptedException e) {
            Log.e(BasicUtil.LOG_TAG, "Error when requestToMyServer. " + e.toString());
            return false;
        }
    }

    public static Pair<Pair<String, String>, String> getEstimateForFilm(String pk, String user) {
        String url = "http://109.234.36.127:8000/dasha/getMyEstimateForFilm?film=" + pk;
        if (user != null) {
            url += "&user=" + user;
        }
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(url);
        try {
            String s = myAsyncTask.get();
            JSONObject jsonObject = new JSONObject(s);
            String num = jsonObject.getString("num");
            String mid = jsonObject.getString("mid");
            String my = jsonObject.has("my") ? jsonObject.getString("my") : "N/A";
            return new Pair<>(new Pair<>(num, mid), my);
        } catch (JSONException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String connectToServer(String s) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(s);
        HttpResponse response = client.execute(request);

// Get the response
        BufferedReader rd = new BufferedReader
                (new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        StringBuilder builder = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    public static Bitmap getImage(String url, String pk) {
        if (url == null || url.equals("N/A")) {
            return MainTabActivity.emptyFilmPoster;
        }
        DownloadImageTask task = new DownloadImageTask();
        Bitmap result;
        task.execute(url);
        try {

            if (!containsFilmImageInDB(pk)) {
                result = task.get();
                ContentValues cv = new ContentValues();
                cv.put("image_data", bitmapToByte(result));
                cv.put("pk", pk);
                // подключаемся к БД
                SQLiteDatabase db = MainTabActivity.dbHelper.getWritableDatabase();
                long rowID = db.insert("tbl_image", null, cv);
            } else {
                result = getImageFromDB(pk);
            }
            Log.d(BasicUtil.LOG_TAG, "Download image :OK ");
        } catch (ExecutionException | InterruptedException e) {
            Log.e(BasicUtil.LOG_TAG, "Error when download image. " + e.toString());
            result = null;
        }
        return result;
    }

    public static Bitmap getImage(String url, Context ctx, String pk) {
        Bitmap image = getImage(url, pk);
        return image != null ? image : BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_launcher);
    }

    public static byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static boolean containsFilmImageInDB(String pk) {
        try {
            SQLiteDatabase db = MainTabActivity.dbHelper.getWritableDatabase();
            String selection = "pk = ?";
            String[] selectionArgs = new String[]{pk};
            Cursor c = db.query("tbl_image", null, selection, selectionArgs, null, null,
                    null);

            if (c != null) {
                return c.moveToFirst();
            }
            return false;
        } finally {
            MainTabActivity.dbHelper.close();
        }
    }

    public static boolean containsFilmInDB(String pk) {
        try {
            SQLiteDatabase db = MainTabActivity.dbHelper.getWritableDatabase();
            String selection = "pk = ?";
            String[] selectionArgs = new String[]{pk};
            Cursor c = db.query("films1", null, selection, selectionArgs, null, null,
                    null);

            if (c != null) {
                return c.moveToFirst();
            }
            return false;
        } finally {
            MainTabActivity.dbHelper.close();
        }
    }

    public static Bitmap getImageFromDB(String pk) {
        try {

            SQLiteDatabase db = MainTabActivity.dbHelper.getWritableDatabase();
            String selection = "pk = ?";
            String[] selectionArgs = new String[]{new Integer(pk).toString()};
            Cursor c = db.query("tbl_image", null, selection, selectionArgs, null, null,
                    null);

            if (c != null) {
                c.moveToFirst();
                int index = c.getColumnIndex("image_data");
                byte[] blob = c.getBlob(index);
                return getImageFromByteArray(blob);
            }

            return null;
        } finally {
            MainTabActivity.dbHelper.close();
        }
    }

    //from bd
    public static Film getFilmFromDB(String pk, String user) {
        try {

            SQLiteDatabase db = MainTabActivity.dbHelper.getWritableDatabase();
            String selection = "pk = ?";
            String[] selectionArgs = new String[]{pk};
            Cursor c = db.query("films1", null, selection, selectionArgs, null, null,
                    null);

            if (c != null) {
                c.moveToFirst();
                int index_pk = c.getColumnIndex("pk");
                int name = c.getColumnIndex("name");
                int name_rus = c.getColumnIndex("name_rus");
                int title = c.getColumnIndex("title");
                int title_rus = c.getColumnIndex("title_rus");
                int actors = c.getColumnIndex("actors");
                int genres = c.getColumnIndex("genres");
                int directors = c.getColumnIndex("directors");
                int time = c.getColumnIndex("time");
                int year = c.getColumnIndex("year");
                int imbdRating = c.getColumnIndex("imbdRating");
                int poster_link = c.getColumnIndex("poster");

                Film film = new Film(c.getInt(index_pk), c.getString(name));
                film.name_rus = c.isNull(name_rus) ? "N/A" : c.getString(name_rus);
                film.title = c.isNull(title) ? "N/A" : c.getString(title);
                film.title_rus = c.isNull(title_rus) ? "N/A" : c.getString(title_rus);
                film.actors = c.isNull(actors) ? "N/A" : c.getString(actors);
                film.genres = c.isNull(genres) ? "N/A" : c.getString(genres);
                film.directors = c.isNull(directors) ? "N/A" : c.getString(directors);
                film.delay = c.isNull(time) ? "N/A" : c.getString(time);
                film.year = c.isNull(year) ? "N/A" : c.getString(year);
                film.imbdRating = c.isNull(imbdRating) ? "N/A" : ((Double) c.getDouble(imbdRating)).toString();
                film.poster = c.isNull(poster_link) ? "N/A" : c.getString(poster_link);

                Pair<Pair<String, String>, String> estimateForFilm = getEstimateForFilm(film.pk,user);
                film.est_num = estimateForFilm.first.first;
                film.est_mid = estimateForFilm.first.second;
                film.myRating = estimateForFilm.second;
                return film;
            }

            return null;
        } finally {
            MainTabActivity.dbHelper.close();
        }
    }

    public static String getUser(){
        SQLiteDatabase db = MainTabActivity.dbHelper.getWritableDatabase();

        Cursor c = db.query("films1", null, null, null, null, null,
                null);

        if (c != null) {
            c.moveToFirst();
            String login = c.getString(c.getColumnIndex("login"));
            return login;
        }
        return null;
    }

    public static void addUser(String password, String name, String age, String sex){
        String url = "http://109.234.36.127:8000/dasha/addUser?name=" + name + "&age=" + age + "&sex=" + sex;
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(url);
        try {
            String s = myAsyncTask.get();
            if(s.equals("OK")){
                ContentValues cv = new ContentValues();

                cv.put("login", name);
                SQLiteDatabase db = MainTabActivity.dbHelper.getWritableDatabase();
                long rowID = db.insert("userTable", null, cv);
                MainTabActivity.dbHelper.close();
                MainTabActivity.user=name;
            }
            return ;
        } catch (ExecutionException | InterruptedException e) {
            Log.e(BasicUtil.LOG_TAG, "Error when requestToMyServer. " + e.toString());
            return;
        }

    }

    public static boolean containsUser(String name){
        String url = "http://109.234.36.127:8000/dasha/containsUser?name=" + name;
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(url);
        try {
            String s = myAsyncTask.get();
            if(s.equals("Yes")){
                MainTabActivity.user=name;
                return true;
            }
        } catch (ExecutionException | InterruptedException e) {
            Log.e(BasicUtil.LOG_TAG, "Error when requestToMyServer. " + e.toString());
            return false;
        }
        return false;

    }

    public static Bitmap getImageFromByteArray(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static Map<String, String> getFilmByPk(String pk, String user) {

        if (containsFilmInDB(pk)) {
            return getFilmFromDB(pk, user).createMap();
        } else {
            Map<String, String> filmmap = requestToMyServer("http://109.234.36.127:8000/dasha/getFilmSmart/?pk=" + pk).get(0);
            ContentValues cv = new ContentValues();

            cv.put("pk", filmmap.get("pk"));
            if (filmmap.get("name") != null) cv.put("name", filmmap.get("name"));

            if (filmmap.get("name_rus") != null) cv.put("name_rus", filmmap.get("name_rus"));
            if (filmmap.get("title") != null) cv.put("title", filmmap.get("title"));
            if (filmmap.get("title_rus") != null) cv.put("title_rus", filmmap.get("title_rus"));
            if (filmmap.get("actors") != null) cv.put("actors", filmmap.get("actors"));
            if (filmmap.get("directors") != null) cv.put("directors", filmmap.get("directors"));
            if (filmmap.get("time") != null) cv.put("time", filmmap.get("time"));
            if (filmmap.get("year") != null) cv.put("year", filmmap.get("year"));
            if (filmmap.get("imbdRating") != null)
                cv.put("imbdRating", new Double(filmmap.get("imbdRating")));
            if (filmmap.get("poster") != null) cv.put("poster", filmmap.get("poster"));
            if (filmmap.get("genres") != null) cv.put("genres", filmmap.get("genres"));
            // подключаемся к БД
            SQLiteDatabase db = MainTabActivity.dbHelper.getWritableDatabase();
            long rowID = db.insert("films1", null, cv);
            MainTabActivity.dbHelper.close();
            return filmmap;
        }
    }
}
