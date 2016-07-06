package com.ssdut411.app.questionanswer.model;

import android.app.Application;
import android.content.Context;

/**
 * Application信息
 *
 * Created by yao_han on 2015/11/24.
 */
public class ApplicationInfo extends Application{


    private static Context context;

    public ApplicationInfo() {
        this.context = getApplicationContext();
    }


    public void setContext(Context context) {
        this.context = context;
    }

    public static Context getContext() {

        return context;
    }
}
