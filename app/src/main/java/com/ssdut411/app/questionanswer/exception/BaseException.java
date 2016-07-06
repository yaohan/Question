package com.ssdut411.app.questionanswer.exception;

/**
 * 自定义异常
 * 系统中所有异常都转化为Exceptions
 * Created by yao_han on 2015/12/22.
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BaseException() {
        super();
    }

    public BaseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public BaseException(String detailMessage) {
        super(detailMessage);
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }
}
