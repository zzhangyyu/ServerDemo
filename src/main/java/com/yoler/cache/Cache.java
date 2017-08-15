package com.yoler.cache;

/**
 * Created by zhangyu on 2017/6/27.
 */
public interface Cache<K, V> {
    V get(K k);

    V put(K k, V v);

    V remove(K k);
}
