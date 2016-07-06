package com.ssdut411.app.questionanswer.utils;

import android.util.Log;

/**
 * Log统一管理类
 *
 * Created by yao_han on 2015/11/24.
 */
public class L {
    private L() {
        throw new UnsupportedOperationException("Log util can't be instantiated");
    }

    public static boolean isDebug = true; //是否需要打印bug，可以在application的onCreate函数里面初始化

    private static final String TAG = "yao";

    public static void i(String msg){
        Log.i(TAG, msg);
    }

    public static void d(String msg){
        Log.d(TAG, msg);
    }

    public static void e(String msg){
        Log.e(TAG, msg);
    }

    public static void v(String msg){
        Log.v(TAG, msg);
    }
}
