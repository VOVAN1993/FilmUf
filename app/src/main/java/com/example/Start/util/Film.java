package com.example.Start.util;

import java.util.Map;
import java.util.TreeMap;

public class Film {
    public static String KEY_NAME = "name";
    public static String KEY_NAME_RUS = "name_rus";
    public static String KEY_DIRECTORS = "directors";
    public static String KEY_ACTORS = "actors";
    public static String KEY_DELAY = "delay";
    public static String KEY_POSTER = "poster";
    public static String KEY_TITLE = "title";
    public static String KEY_TITLE_RUS = "title_rus";
    public static String KEY_IMBDRATING = "imbdRating";
    public static String KEY_GENRES = "genres";
    public static String KEY_YEAR = "year";
    public static String KEY_PK = "pk";
    public static String KEY_EST_NUM = "est_num";
    public static String KEY_EST_MID = "est_mid";
    public static String KEY_USER_RATING = "userRating";
    public  String name;
    public  String name_rus;
    public  String directors;
    public  String actors;
    public  String delay;
    public  String poster;
    public  String title;
    public  String title_rus;
    public  String est_num;
    public  String est_mid;
    public  String genres;
    public  String imbdRating;
    public  String year;
    public  String pk;
    public  String myRating = "N/A";

    public Film(String name) {
        this.name = name;
    }

    public Film(int pk, String name) {
        this.pk = new Integer(pk).toString();
        this.name = name;
    }

    public Map<String, String> createMap(){
        Map<String,String> map = new TreeMap<>();
        BasicUtil.putIfNotNA(map, KEY_NAME, name);
        BasicUtil.putIfNotNA(map, KEY_NAME_RUS, name_rus);
        BasicUtil.putIfNotNA(map, KEY_DIRECTORS, directors);
        BasicUtil.putIfNotNA(map, KEY_ACTORS, actors);
        BasicUtil.putIfNotNA(map, KEY_DELAY, delay);
        BasicUtil.putIfNotNA(map, KEY_POSTER, poster);
        BasicUtil.putIfNotNA(map, KEY_TITLE, title);
        BasicUtil.putIfNotNA(map, KEY_TITLE_RUS, title_rus);
        BasicUtil.putIfNotNA(map, KEY_IMBDRATING, imbdRating);
        BasicUtil.putIfNotNA(map, KEY_GENRES, genres);
        BasicUtil.putIfNotNA(map, KEY_YEAR, year);
        BasicUtil.putIfNotNA(map, KEY_PK, pk);
        BasicUtil.putIfNotNA(map, KEY_EST_MID, est_mid);
        BasicUtil.putIfNotNA(map, KEY_EST_NUM, est_num);
        BasicUtil.putIfNotNA(map, KEY_USER_RATING, myRating);
        return map;
    }
}
