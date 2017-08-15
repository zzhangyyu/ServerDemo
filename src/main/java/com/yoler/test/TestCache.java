package com.yoler.test;

import com.yoler.cache.ProvinceCache;

/**
 * 测试缓存
 * Created by zhangyu on 2017/6/27.
 */
public class TestCache {
    public static void main(String[] args) {
        ProvinceCache provinceCache = ProvinceCache.getInstance();
        System.out.println("插入一条缓存...");
        provinceCache.put("北京", "31");
        System.out.println("    读取插入的缓存...：" + provinceCache.get("北京"));
        System.out.println("    读取一条没有的缓存...：" + provinceCache.get("上海"));

        System.out.println();
        System.out.println("移除缓存...");
        provinceCache.remove("北京");
        System.out.println("    读取已移除的缓存...：" + provinceCache.get("北京"));
    }
}
