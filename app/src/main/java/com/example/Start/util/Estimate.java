package com.example.Start.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Estimate {
    public Film film;
    public String estimate;
    public User user;
    public Date date;
    public String pk;
    public String poster;

    public static String ESTIMATE_ATTRIBUTE_FILM = "estimateFilm";
    public static String ESTIMATE_ATTRIBUTE_FILM_POSTER = "estimateFilmPoster";
    public static String ESTIMATE_ATTRIBUTE_FILM_PK = "estimateFilmPk";
    public static String ESTIMATE_ATTRIBUTE_ESTIMATE = "estimateEstimate";
    public static String ESTIMATE_ATTRIBUTE_USER = "estimateUser";
    public static String ESTIMATE_ATTRIBUTE_DATE = "estimateDate";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public Estimate(String estimate, User user, Film film, Date date) {
        this.estimate = estimate;
        this.user = user;
        this.film = film;
        this.date = date;
    }

    public Map<String, Object> toMap(){
        TreeMap<String, Object> map = new TreeMap<>();
        map.put(ESTIMATE_ATTRIBUTE_FILM,film.name);
        map.put(ESTIMATE_ATTRIBUTE_ESTIMATE,estimate);
        map.put(ESTIMATE_ATTRIBUTE_USER,user.name);
        map.put(ESTIMATE_ATTRIBUTE_DATE,dateFormat.format(date));
        map.put(ESTIMATE_ATTRIBUTE_FILM_PK, film.pk);

        return map;
    }

    public Map<String,Object> toMapWithPoster(){
        Map<String,Object> map = toMap();
        map.put(ESTIMATE_ATTRIBUTE_FILM_POSTER, film.pk + "end@" + film.poster);
        return map;
    }
}
