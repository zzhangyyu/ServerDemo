package com.yoler.cache;


import java.util.Date;
import java.util.Hashtable;

/**
 * Created by zhangyu on 2017/6/27.
 */
public class BaseDataCache<K, V> implements Cache<K, V> {
    /**
     * 有效期，秒
     */
    protected int timeOut;
    /**
     * 是否开启，
     */
    protected boolean cacheEnable;
    /**
     * 缓存值
     */
    protected Hashtable baseData;

    @Override
    public V get(K k) {
        if (!cacheEnable) {
            return null;
        }
        Object vTimeStr = baseData.get(k + CacheKey.timeSuffix);
        if (vTimeStr == null) {
            return null;
        }
        long vTime = Long.parseLong(vTimeStr.toString());
        if (new Date().getTime() - vTime > timeOut * 1000) {
            return null;
        }
        return (V) baseData.get(k);
    }

    @Override
    public V put(K k, V v) {
        if (!cacheEnable) {
            return v;
        }
        //写入缓存时，需要写入两个键值对，一个为缓存的key-value，另一个为缓存存储时间的key-value
        baseData.put(k, v);
        baseData.put(k + "time", new Date().getTime());
        return v;
    }

    @Override
    public V remove(K k) {
        V v = (V) baseData.get(k);
        baseData.remove(k);
        baseData.remove(k + CacheKey.timeSuffix);
        return v;
    }
}
