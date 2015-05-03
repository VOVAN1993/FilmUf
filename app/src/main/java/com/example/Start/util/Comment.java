package com.example.Start.util;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Comment {
    public Film film;
    public String comment;
    public User user;
    public Date date;

    public static String COMMENT_ATTRIBUTE_FILM = "commentFilm";
    public static String COMMENT_ATTRIBUTE_COMMENT = "commentComment";
    public static String COMMENT_ATTRIBUTE_USER = "commentUser";
    public static String COMMENT_ATTRIBUTE_DATE = "commentDate";

    public Comment(String comment, User user, Film film, Date date) {
        this.comment = comment;
        this.user = user;
        this.film = film;
        this.date = date;
    }

    public Map<String, String> toMap(){
        TreeMap<String, String> map = new TreeMap<>();
        map.put(COMMENT_ATTRIBUTE_FILM,film.name);
        map.put(COMMENT_ATTRIBUTE_COMMENT,comment);
        map.put(COMMENT_ATTRIBUTE_USER,user.name);
        map.put(COMMENT_ATTRIBUTE_DATE,date.toString());

        return map;
    }
}
