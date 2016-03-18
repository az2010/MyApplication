package test.owen.com.myapplication.emotion;

import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;

/**
 * Created by winghe on 2015/11/23.
 */
public enum EmCache {
    instance;

    private static final int SIZE = 50;
    private LruCache<String, Drawable> mCache;

    EmCache() {
        mCache = new LruCache<String, Drawable>(SIZE);
    }

    public static EmCache getInstance(){
        return instance;
    }

    public void put(String key, Drawable value) {
        mCache.put(key, value);
    }

    public Drawable get(String key) {
        return mCache.get(key);
    }
}
