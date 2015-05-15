package com.example.Start.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
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
}
