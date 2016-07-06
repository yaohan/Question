package com.ssdut411.app.questionanswer.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 *
 * Created by yao_han on 2015/11/24.
 */
public class GsonUtils {
    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }
    }

    private GsonUtils() {

    }

    /**
     * 将对象转换为jsonString
     *
     * @param o
     * @return
     */
    public static String gsonToJsonString(Object o) {
        String jsonString = null;
        if (gson != null) {
            jsonString = gson.toJson(o);
        }
        return jsonString;
    }

    /**
     * 将jsonString转换为object
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToObject(String jsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(jsonString, cls);
        }
        return t;
    }

    /**
     * 将jsonString转换为list
     *
     * @param jsonString
     * @return
     */
    public static <T> List<T> gsonToList(String jsonString) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    public static <T> List<T> gsonToList(String jsonString,Class<T> clazz){
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(jsonString).getAsJsonArray();
        for(final JsonElement element:array){
            list.add(gson.fromJson(element,clazz));
        }
        return list;
    }

    /**
     * 将jsonString转换为带有map的list
     *
     * @param jsonString
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMap(String jsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(jsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 将jsonString转换为map
     *
     * @param jsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMap(String jsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(jsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
