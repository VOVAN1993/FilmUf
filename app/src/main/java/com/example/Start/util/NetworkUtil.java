package com.example.Start.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.Start.R;
import com.example.Start.activity.MainTabActivity;
import com.example.Start.util.asyncTasks.DownloadImageTask;
import com.example.Start.util.asyncTasks.MyAsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class NetworkUtil {

    public static ArrayList<Map<String,String>> requestToMyServer(String url){
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

    public static Bitmap getImage(String url, String pk){
        if(url == null || url.equals("N/A")){
            return MainTabActivity.emptyFilmPoster;
        }
        DownloadImageTask task = new DownloadImageTask();
        Bitmap result;
        task.execute(url);
        try {

            if(!containsFilmImageInDB(pk)) {
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
            Log.d(BasicUtil.LOG_TAG, "Download image :OK " );
        } catch (ExecutionException | InterruptedException e) {
            Log.e(BasicUtil.LOG_TAG, "Error when download image. " + e.toString());
            result = null;
        }
        return result ;
    }

    public static Bitmap getImage(String url, Context ctx, String pk){
        Bitmap image = getImage(url, pk);
        return image!=null ? image :  BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_launcher);
    }

    public static byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static boolean containsFilmImageInDB(String pk) {
        try {
            SQLiteDatabase db = MainTabActivity.dbHelper.getWritableDatabase();
            String selection = "pk = ?";
            String[] selectionArgs = new String[]{new Integer(pk).toString()};
            Cursor c = db.query("tbl_image", null, selection, selectionArgs, null, null,
                    null);

            if (c != null) {
                return c.moveToFirst();
            }
            return false;
        }finally {
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
        }finally {
            MainTabActivity.dbHelper.close();
        }
    }

    public static Bitmap getImageFromByteArray(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
