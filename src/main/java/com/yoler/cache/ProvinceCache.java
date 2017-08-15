package com.yoler.cache;


import java.util.Hashtable;

/**
 * Created by zhangyu on 2017/6/27.
 */
public class ProvinceCache extends BaseDataCache<String, Object> {
    private ProvinceCache() {
        cacheEnable = true;
        timeOut = 3600;
        baseData = new Hashtable();
    }

    private static ProvinceCache instance = new ProvinceCache();

    public static ProvinceCache getInstance() {
        return instance;
    }
}
