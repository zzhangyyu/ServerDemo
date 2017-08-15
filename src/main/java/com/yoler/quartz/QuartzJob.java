package com.yoler.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangyu on 2017/7/17.
 */
public class QuartzJob {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzJob.class);

    public void run() {
        LOG.info("数据转换任务线程开始执行");
    }
}
