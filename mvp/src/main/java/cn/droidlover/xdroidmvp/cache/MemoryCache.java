package cn.droidlover.xdroidmvp.cache;

import android.text.TextUtils;

import androidx.collection.LruCache;

/**
 * Created by wanglei on 2016/11/28.
 */

public class MemoryCache implements ICache {

    private LruCache<String, String> cache;
    private static MemoryCache instance;

    private MemoryCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        cache = new LruCache<>(cacheSize);

    }

    public static MemoryCache getInstance() {
        if (instance == null) {
            synchronized (MemoryCache.class) {
                if (instance == null) {
                    instance = new MemoryCache();
                }
            }
        }
        return instance;
    }


    @Override
    public synchronized void put(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }

        if (cache.get(key) != null) {
            cache.remove(key);
        }
        cache.put(key, value);
    }

    @Override
    public String get(String key) {
        return cache.get(key);
    }

    public synchronized <T> T get(String key, Class<T> clazz) {
        try {
            return (T) cache.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(String key) {
        if (cache.get(key) != null) {
            cache.remove(key);
        }
    }

    @Override
    public boolean contains(String key) {
        return cache.get(key) != null;
    }

    @Override
    public void clear() {
        cache.evictAll();
    }
}
