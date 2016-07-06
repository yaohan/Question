package com.ssdut411.app.questionanswer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by yao_han on 2016/3/28.
 */
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }


    public void closeDB() {
        db.close();
    }

}
