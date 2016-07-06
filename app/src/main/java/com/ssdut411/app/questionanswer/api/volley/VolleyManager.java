package com.ssdut411.app.questionanswer.api.volley;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.ssdut411.app.questionanswer.utils.L;

/**
 * Created by yao_han on 2015/11/24.
 */
public class VolleyManager {

    // 超时时间（毫秒）
    private static final int TIME_OUT = 10 * 1000;

    // 最大重连次数，1表示重连2次
    private static final int MAX_RETRIES = 1;

    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;

    private VolleyManager() {

    }

    /**
     * 初始化
     * <p/>
     * 最好是在application的onCreate方法里面初始化
     *
     * @param context
     */
    public static void init(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new BitmapCache(context));
    }

    /**
     * 得到请求队列
     *
     * @return
     */
    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    /**
     * 得到ImageLoader
     *
     * @return
     */
    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * 添加一个请求到RequestQueue队列
     *
     * @param request
     * @param tag
     */
    public static <T> void addRequest(Request<T> request, Object tag) {
        if (null != tag) {
            request.setTag(tag);
        }

        // 设置超时时间10s,最大重连次数2次
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        L.i("requestQueue == null?" + (requestQueue == null));
        requestQueue.add(request);
    }

    /**
     * 取消RequestQueue队列的请求
     *
     * @param tag
     */
    public static void cancelAll(Object tag) {
        requestQueue.cancelAll(tag);
    }
}
