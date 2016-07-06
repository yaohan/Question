package com.ssdut411.app.questionanswer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * SharedPreferences 封装类
 *
 * Created by yao_han on 2015/11/25.
 */
public class SPUtils {
    /**
     * 保存在手机里的文件名
     */
    public static final String FILE_NAME = "QuestionSharedPreferences";

    /**
     * 保存数据的方法，根据类型调用不同的方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(object instanceof  String){
            editor.putString(key, (String)object);
        }else if(object instanceof  Integer){
            editor.putInt(key, (Integer) object);
        }else if(object instanceof  Boolean){
            editor.putBoolean(key, (Boolean) object);
        }else if(object instanceof  Float){
            editor.putFloat(key, (Float) object);
        }else{
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    /**
     * 得到保存数据的方法，根据默认值得到保存的数据类型，调用对应方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject){
//        L.i("context == null?"+(context == null));
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if(defaultObject instanceof String){
            return sp.getString(key, (String) defaultObject);
        }else if(defaultObject instanceof  Integer){
            return sp.getInt(key, (Integer)defaultObject);
        }else if(defaultObject instanceof  Boolean){
            return sp.getBoolean(key, (Boolean) defaultObject);
        }else if(defaultObject instanceof  Float){
            return sp.getFloat(key, (Float)defaultObject);
        }else{
            return null;
        }
    }

    /**
     * 移除某个key对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        return sp.getAll();
    }

}
