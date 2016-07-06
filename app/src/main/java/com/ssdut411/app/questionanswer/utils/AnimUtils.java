package com.ssdut411.app.questionanswer.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.ssdut411.app.questionanswer.R;

/**
 * Created by yao_han on 2016/3/3.
 */
public class AnimUtils {
    /**
     * 向左旋转90度
     */
    public static void rotateLeft(Context context, View view){
        view.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.rotate_left));
    }

    /**
     * 向右旋转90度
     */
    public static void rotateRight(Context context, View view){
        view.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.rotate_right));
    }

    /**
     * 向上收回View
     */
    public static void translateCloseUp(Context context, final View view){
        view.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.translate_close_up));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                L.i("隐藏前");
                view.setVisibility(View.GONE);
                L.i("隐藏后");
            }
        }, 500);
    }

    /**
     * 向下展开View
     */
    public static void translateOpenDown(Context context, View view){
        L.i("显示前");
        view.setVisibility(View.VISIBLE);
        L.i("显示后");
        view.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.translate_open_down));
    }

    /**
     * 向下收回View
     */
    public static void translateCloseDown(Context context, final View view){
        view.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.translate_close_down));
        view.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
                L.i("隐藏");
            }
        }, 550);
    }

    /**
     * 向上展开View
     */
    public static void translateOpenUp(Context context, View view){
        view.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.translate_open_up));
        view.setVisibility(View.VISIBLE);
    }
}
