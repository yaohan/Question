package com.ssdut411.app.questionanswer.api.volley;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;


/**
 * 图片缓存类
 * Created by yao_han on 2015/11/24.
 */

public class BitmapCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> cache;

    // 图片在内存中的缓存大小，一般为总内存的1/8
    private int cacheSize = 0;

    public BitmapCache(Context context) {
        int memorySize = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        cacheSize = 1024 * 1024 * memorySize / 8;

        cache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return super.sizeOf(key, value);
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }

}