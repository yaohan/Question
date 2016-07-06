package com.ssdut411.app.questionanswer.exception;


import com.ssdut411.app.questionanswer.utils.ActivityStackUtils;

import java.text.DateFormat;

/**
 * 异常处理基类
 * Created by yao_han on 2015/12/22.
 */
public abstract class BaseExceptionHandler implements Thread.UncaughtExceptionHandler {

    protected static final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);

    // 延迟时间（毫秒）
    private static final long DELAY_TIME = 3000;

    /**
     * 未捕获异常处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 如果处理了未捕获异常
        if (this.exceptionHandle(ex)) {
            try {
                // 让程序继续运行3秒后再退出，保证错误日志的保存
                Thread.sleep(DELAY_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 退出程序
            ActivityStackUtils.getInstance().exit();
        }
    }

    /**
     * 自定义异常处理,收集错误信息，发送错误报告等操作均在此完成. 可以根据情况来自定义异常处理逻辑
     *
     * @param ex
     * @return
     */
    protected abstract boolean exceptionHandle(Throwable ex);
}
