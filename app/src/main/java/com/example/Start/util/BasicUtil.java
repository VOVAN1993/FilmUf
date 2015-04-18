package com.example.Start.util;

import java.util.Map;

public class BasicUtil {
    public final static String LOG_TAG = "myLogs";


    public static<K,V> void putIfNotNull(Map<K,V> map, K key, V value){
        if(key!=null){
            map.put(key,value);
        }
    }
}
