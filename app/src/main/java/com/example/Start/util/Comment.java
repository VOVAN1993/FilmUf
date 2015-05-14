package com.example.Start.util;

import com.example.Start.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Comment {
    private String dislikes;
    private String likes;
    public Film film;
    public String year;
    public String comment;
    public User user;
    public Date date;
    public String pk;

    public static String COMMENT_ATTRIBUTE_FILM = "commentFilm";
    public static String COMMENT_ATTRIBUTE_FILM_POSTER = "commentFilmPoster";
    public static String COMMENT_ATTRIBUTE_YEAR = "commentFYear";
    public static String COMMENT_ATTRIBUTE_COMMENT = "commentComment";
    public static String COMMENT_ATTRIBUTE_USER = "commentUser";
    public static String COMMENT_ATTRIBUTE_DATE = "commentDate";
    public static String COMMENT_ATTRIBUTE_PK = "commentPK";
    public static String COMMENT_ATTRIBUTE_LIKES = "commentLikes";
    public static String COMMENT_ATTRIBUTE_DISLIKES = "commentDislikes";

//    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd HH:mm:ss");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");


    public Comment(String comment, User user, Film film, String year, Date date, String pk,
                   String likes,String dislikes) {
        this.comment = comment;
        this.user = user;
        this.film = film;
        this.year = year;
        this.date = date;
        this.pk = pk;
        this.likes=likes;
        this.dislikes=dislikes;
    }

    public Map<String, Object> toMap(){
        TreeMap<String, Object> map = new TreeMap<>();
        map.put(COMMENT_ATTRIBUTE_FILM,film.name);
        map.put(COMMENT_ATTRIBUTE_YEAR,year);
        map.put(COMMENT_ATTRIBUTE_COMMENT,comment);
        map.put(COMMENT_ATTRIBUTE_USER,user.name);
        map.put(COMMENT_ATTRIBUTE_PK, pk);
        map.put(COMMENT_ATTRIBUTE_DISLIKES, dislikes);
        map.put(COMMENT_ATTRIBUTE_LIKES, likes);
        map.put(COMMENT_ATTRIBUTE_DATE,dateFormat.format(date));

        return map;
    }

    public Map<String, Object> toMapWithImage(){
        Map<String, Object> map = toMap();
        map.put(COMMENT_ATTRIBUTE_FILM_POSTER, film.pk + "end@" + film.poster);
//        map.put(COMMENT_ATTRIBUTE_FILM_POSTER, NetworkUtil.getImage(film.poster, film.pk));

        return map;
    }
}
