package com.example.Start.util;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Estimate {
    public Film film;
    public String estimate;
    public User user;
    public Date date;

    public static String ESTIMATE_ATTRIBUTE_FILM = "estimateFilm";
    public static String ESTIMATE_ATTRIBUTE_ESTIMATE = "estimateEstimate";
    public static String ESTIMATE_ATTRIBUTE_USER = "estimateUser";
    public static String ESTIMATE_ATTRIBUTE_DATE = "estimateDate";

    public Estimate(String estimate, User user, Film film, Date date) {
        this.estimate = estimate;
        this.user = user;
        this.film = film;
        this.date = date;
    }

    public Map<String, String> toMap(){
        TreeMap<String, String> map = new TreeMap<>();
        map.put(ESTIMATE_ATTRIBUTE_FILM,film.name);
        map.put(ESTIMATE_ATTRIBUTE_ESTIMATE,estimate);
        map.put(ESTIMATE_ATTRIBUTE_USER,user.name);
        map.put(ESTIMATE_ATTRIBUTE_DATE,date.toString());

        return map;
    }
}
