package com.example.Start.util;

import android.util.Log;

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
            String tmp = s;
            String[] split = tmp.split(Pattern.quote("\"") + "fields"+ Pattern.quote("\"")+Pattern.quote(":"));
            JSONArray jsonArray = new JSONArray(s);
            Film film = null;
            JSONArray json = new JSONArray(s);
            for (int i = 1; i < split.length; i++) {
                JSONObject jsonObject = new JSONObject(split[i]);
                if(jsonObject.isNull("name")){
                    throw new IllegalArgumentException("Name in json is null");
                }

                film = new Film(jsonObject.getString("name"));
                film.name_rus = jsonObject.isNull("name_rus")?"N/A":jsonObject.getString("name_rus");
                film.actors = jsonObject.isNull("actors")?"N/A":arrayToString(jsonObject.getJSONArray("actors"));
                film.directors = jsonObject.isNull("directors")?"N/A":arrayToString(jsonObject.getJSONArray("directors"));
                film.genres = jsonObject.isNull("genres")?"N/A":arrayToString(jsonObject.getJSONArray("genres"));
                film.delay = jsonObject.isNull("time")?"N/A":jsonObject.getString("time");
                film.imbdRating = jsonObject.isNull("imbdRating")?"N/A":jsonObject.getString("imbdRating");
                film.poster = jsonObject.isNull("poster_link")?"N/A":jsonObject.getString("poster_link");
                film.title = jsonObject.isNull("title")?"N/A":jsonObject.getString("title");
                film.title_rus = jsonObject.isNull("title_rus")?"N/A":jsonObject.getString("title_rus");
                result.add(film);
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
}
