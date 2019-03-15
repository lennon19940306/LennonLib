package com.lennon.cn.utill.cache;

import android.content.Context;

import cn.droidlover.xdroidmvp.cache.DiskCache;
import cn.droidlover.xdroidmvp.cache.ICache;
import cn.droidlover.xdroidmvp.cache.MemoryCache;

public class ACache implements ICache {
    DiskCache diskCache;
    MemoryCache memoryCache;
    public static ACache aCache;

    private ACache(Context context) {
        diskCache = DiskCache.getInstance(context);
        memoryCache = MemoryCache.getInstance();
    }

    public static ACache getInstance(Context context) {
        if (aCache == null) {
            aCache = new ACache(context);
        }
        return aCache;

    }

    @Override
    public synchronized void put(String key, String value) {
        memoryCache.put(key, value);
        diskCache.put(key, value);
    }

    @Override
    public String get(String key) {
        if (memoryCache.contains(key)) {
            return memoryCache.get(key);
        } else if (diskCache.contains(key)) {
            String s = diskCache.get(key);
            memoryCache.put(key, s);
            return s;
        }
        return "";
    }

    @Override
    public void remove(String key) {
        memoryCache.remove(key);
        diskCache.remove(key);
    }

    @Override
    public boolean contains(String key) {
        return memoryCache.contains(key) || diskCache.contains(key);
    }

    @Override
    public void clear() {
        memoryCache.clear();
        diskCache.clear();
    }
}
