package com.example.Start.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Comment {
    private String dislikes;
    private String likes;
    public Film film;
    public String comment;
    public User user;
    public Date date;
    public String pk;

    public static String COMMENT_ATTRIBUTE_FILM = "commentFilm";
    public static String COMMENT_ATTRIBUTE_COMMENT = "commentComment";
    public static String COMMENT_ATTRIBUTE_USER = "commentUser";
    public static String COMMENT_ATTRIBUTE_DATE = "commentDate";
    public static String COMMENT_ATTRIBUTE_PK = "commentPK";
    public static String COMMENT_ATTRIBUTE_LIKES = "commentLikes";
    public static String COMMENT_ATTRIBUTE_DISLIKES = "commentDislikes";

//    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd HH:mm:ss");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");


    public Comment(String comment, User user, Film film, Date date, String pk,
                   String likes,String dislikes) {
        this.comment = comment;
        this.user = user;
        this.film = film;
        this.date = date;
        this.pk = pk;
        this.likes=likes;
        this.dislikes=dislikes;
    }

    public Map<String, String> toMap(){
        TreeMap<String, String> map = new TreeMap<>();
        map.put(COMMENT_ATTRIBUTE_FILM,film.name);
        map.put(COMMENT_ATTRIBUTE_COMMENT,comment);
        map.put(COMMENT_ATTRIBUTE_USER,user.name);
        map.put(COMMENT_ATTRIBUTE_PK, pk);
        map.put(COMMENT_ATTRIBUTE_DISLIKES, dislikes);
        map.put(COMMENT_ATTRIBUTE_LIKES, likes);
        map.put(COMMENT_ATTRIBUTE_DATE,dateFormat.format(date));

        return map;
    }
}
