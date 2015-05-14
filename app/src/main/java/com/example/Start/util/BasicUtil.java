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

            String[] pks = tmp.split("pk");
            Film film = null;
            for(int i=1;i<pks.length;i++){
                String str = pks[i];
                str = "{\"" + "pk" + str;
                if(str.lastIndexOf("][{") >0){
                    str = str.substring(0,str.lastIndexOf("][{"));
                }
                JSONObject jsonObject = new JSONObject(str);
                int pk = jsonObject.getInt("pk");

                JSONObject filmJson = jsonObject.getJSONObject("fields");
                if(filmJson.isNull("name")){
                    throw new IllegalArgumentException("Name in json is null");
                }

                film = new Film(pk, filmJson.getString("name"));
                film.name_rus = filmJson.isNull("name_rus")?"N/A":filmJson.getString("name_rus");
                film.actors = filmJson.isNull("actors")?"N/A":arrayToString(filmJson.getJSONArray("actors"));
                film.directors = filmJson.isNull("directors")?"N/A":arrayToString(filmJson.getJSONArray("directors"));
                film.genres = filmJson.isNull("genres")?"N/A":arrayToString(filmJson.getJSONArray("genres"));
                film.delay = filmJson.isNull("time")?"N/A":filmJson.getString("time");
                film.imbdRating = filmJson.isNull("imbdRating")?"N/A":filmJson.getString("imbdRating");
                film.poster = filmJson.isNull("poster_link")?"N/A":filmJson.getString("poster_link");
                film.title = filmJson.isNull("title")?"N/A":filmJson.getString("title");
                film.title_rus = filmJson.isNull("title_rus")?"N/A":filmJson.getString("title_rus");
                film.year = filmJson.isNull("year")?"N/A":filmJson.getString("year");

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
}
