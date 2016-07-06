package com.ssdut411.app.questionanswer.utils;

import android.os.Environment;

/**
 * Created by yao_han on 2015/12/22.
 */
public class SDCardUtils {
    /**
     * 检查SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SDCard的路径
     *
     * @return
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable()) {
            throw new com.ssdut411.app.questionanswer.exception.BaseException("SDCard不可用");
        }

        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();

        return sdcard;

    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

}