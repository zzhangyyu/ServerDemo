package com.yoler.test;

import com.yoler.callBack.Li;
import com.yoler.callBack.Wang;

/**
 * 测试回调
 * Created by zhangyu on 2017/6/21.
 */
public class TestCallBack {
    public static void main(String[] args) {
        /**
         * new 一个小李
         */
        Li li = new Li();

        /**
         * new 一个小王
         */
        Wang wang = new Wang(li);

        /**
         * 小王问小李问题
         */
        wang.askQuestion("1 + 1 = ?");
    }
}
