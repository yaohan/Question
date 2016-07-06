package com.ssdut411.app.questionanswer.api.volley;

import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ssdut411.app.questionanswer.api.ApiCallbackListener;
import com.ssdut411.app.questionanswer.utils.L;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yao_han on 2015/11/24.
 */
public class VolleyUtil {

    // 请求参数的key
    public static final String REQUEST_PARAMS_KEY = "req";
    /**
     * 发送Get请求
     *
     * @param url      请求地址
     * @param cls      要转换的对象
     * @param tag      通常为Activity标记
     * @param callBack 回调接口
     */
    public static <T> void doGet(String url, Class<T> cls, Object tag, final ApiCallbackListener<T> callBack) {
        GsonRequest<T> request = new GsonRequest<T>(Request.Method.GET, url, cls, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                callBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error.getMessage());
            }
        });
        VolleyManager.addRequest(request, tag);
    }

    /**
     * 发送Post请求
     *
     * @param url      请求地址
     * @param reqJson  请求参数json串
     * @param cls      要转换的对象
     * @param tag      通常为Activity标记
     * @param callBack 回调接口
     */
    public static <T> void doPost(String url, String reqJson, Class<T> cls, Object tag, final ApiCallbackListener<T> callBack) {
        L.i("post:" + reqJson);
        Map<String, String> requestParams = new HashMap<String,String>();
        requestParams.put(REQUEST_PARAMS_KEY, reqJson);
        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, url, requestParams, cls, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                callBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error.getMessage());
            }
        });
        VolleyManager.addRequest(request, tag);
    }

    /**
     * 取消RequestQueue队列的请求
     *
     * @param tag
     */
    public static void cancelAll(Object tag) {
        VolleyManager.cancelAll(tag);
    }

    /**
     * 显示网络图片
     *
     * @param url       图片地址
     * @param imageView 图片组件
     */
    public static void displayImage(String url, ImageView imageView, int defaultImageResId, int errorImageResId) {
        ImageLoader imageLoader = VolleyManager.getImageLoader();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);
        imageLoader.get(url, listener);
    }

    /**
     * 显示网络图片
     *
     * @param url              图片地址
     * @param networkImageView 图片组件
     */
    public static void displayImage(String url, NetworkImageView networkImageView, int defaultImageResId, int errorImageResId) {
        networkImageView.setDefaultImageResId(defaultImageResId);
        networkImageView.setErrorImageResId(errorImageResId);
        networkImageView.setImageUrl(url, VolleyManager.getImageLoader());
    }
}
