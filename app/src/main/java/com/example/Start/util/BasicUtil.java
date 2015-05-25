package com.example.Start.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class BasicUtil {
    public final static String LOG_TAG = "myLogs";


    public static<K,V> void putIfKeyNotNull(Map<K, V> map, K key, V value){
        if(key!=null){
            map.put(key,value);
        }
    }

    public static<K,V> void putIfNotNA(Map<K,V> map, K key, V value){
        if(!value.equals("N/A")){
            map.put(key,value);
        }
    }
    public static Map<String, String> jsonToUserInfo(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s.replace("\"",""));
            System.out.println();
            Map<String, String> map = new TreeMap<>();
//            {"sex":"M","is_my_user":false,"name":"vova","age":18}
            map.put("sex", jsonObject.getString("sex"));
            map.put("name", jsonObject.getString("name"));
            map.put("age", String.valueOf(jsonObject.getInt("age")));
            map.put("you_have_friend", String.valueOf(jsonObject.getBoolean("you_have_friend")));
            map.put("friend_have_you", String.valueOf(jsonObject.getBoolean("friend_have_you")));
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
    public static ArrayList<Film> jsonToFilm(String s){
        ArrayList<Film> result = new ArrayList<>();
        try {
            Film film = null;
            JSONArray array = new JSONArray(s);
            for(int i=0;i<array.length();i++){
                String string = array.getString(i);

                JSONObject jsonObject = new JSONObject(string);
                int pk = jsonObject.getInt("pk");

                if(jsonObject.isNull("name")){
                    throw new IllegalArgumentException("Name in json is null");
                }

                film = new Film(pk, jsonObject.getString("name"));
                film.name_rus = jsonObject.isNull("name_rus")?"N/A":jsonObject.getString("name_rus");
                film.actors = jsonObject.isNull("actors")?"N/A":arrayToString(jsonObject.getJSONArray("actors"));
                film.directors = jsonObject.isNull("directors")?"N/A":arrayToString(jsonObject.getJSONArray("directors"));
                film.genres = jsonObject.isNull("genres")?"N/A":arrayToString(jsonObject.getJSONArray("genres"));
                film.delay = jsonObject.isNull("time")?"N/A":jsonObject.getString("time");
                film.imbdRating = jsonObject.isNull("imbdRating")?"N/A":jsonObject.getString("imbdRating");
                film.poster = jsonObject.isNull("poster_link")?"N/A":jsonObject.getString("poster_link");
                film.title = jsonObject.isNull("title")?"N/A":jsonObject.getString("title");
                film.title_rus = jsonObject.isNull("title_rus")?"N/A":jsonObject.getString("title_rus");
                film.year = jsonObject.isNull("year")?"N/A":jsonObject.getString("year");
                film.est_mid = jsonObject.isNull("est_mid")?"N/A":jsonObject.getString("est_mid");
                film.est_num = jsonObject.isNull("est_num")?"N/A":jsonObject.getString("est_num");
                film.country = jsonObject.isNull("country")?"N/A":arrayToString(jsonObject.getJSONArray("country"));

                result.add(film);

                System.out.println();
            }
        } catch (IllegalArgumentException | JSONException e) {
            Log.e(LOG_TAG, "Error when parse json for create film. "+ e.toString());
        }

        return result;
    }

    private static String arrayToString(JSONArray ar) throws JSONException {
        StringBuffer bfr = new StringBuffer();
        for (int i = 0; i < ar.length(); i++) {
            bfr.append(ar.getString(i));
            if(i!=ar.length()-1) bfr.append(",");
        }
        return bfr.toString();
    }

    public static ArrayList<Map<String,String>> filmsToMaps(ArrayList<Film> films){
        ArrayList<Map<String,String>> ret = new ArrayList<>();
        for(Film film : films){
            ret.add(film.createMap());
        }
        return ret;
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static void scaleImage(ImageView view, int boundBoxInDp)
    {
        // Get the ImageView and its bitmap
        Drawable drawing = view.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawing).getBitmap();

        // Get current dimensions
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) boundBoxInDp) / width;
        float yScale = ((float) boundBoxInDp) / height;
        float scale = xScale;

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        width = scaledBitmap.getWidth();
        height = scaledBitmap.getHeight();

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }
}
