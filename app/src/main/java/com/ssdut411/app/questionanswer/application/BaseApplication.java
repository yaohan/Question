package com.ssdut411.app.questionanswer.application;

import android.app.Application;
import android.content.Context;

import com.ssdut411.app.questionanswer.exception.BaseExceptionHandler;
import com.ssdut411.app.questionanswer.utils.L;


/**
 * Created by yao_han on 2015/12/22.
 */
public abstract class BaseApplication extends Application {
    protected static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
//        L.i("base context set");
        context = this.getApplicationContext();

//        if (this.getDefaultUncaughtExceptionHandler() == null) {
//            Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(applicationContext));
//        } else {
//            Thread.setDefaultUncaughtExceptionHandler(getDefaultUncaughtExceptionHandler());
//        }
    }

    /**
     * 获取默认的未捕获异常处理器
     *
     * @return
     */
    public abstract BaseExceptionHandler getDefaultUncaughtExceptionHandler();
}
