package com.ssdut411.app.questionanswer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yao_han on 2016/3/28.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "question.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS account " +
                "(_id INTEGER PRIMARY KEY, " +
                "phoneNumber VARCHAR, " +
                "password VARCHAR, " +
                "login BOOLEAN, " +
                "role VARCHAR) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
