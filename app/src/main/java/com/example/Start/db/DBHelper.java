package com.example.Start.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.Start.util.BasicUtil;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(BasicUtil.LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        String createQuery = "CREATE TABLE tbl_image (\n" +
                "    _id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    pk INTEGER,\n" +
                "    image_data BLOB\n" +
                ");";

        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
