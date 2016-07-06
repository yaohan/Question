package com.ssdut411.app.questionanswer.core;

/**
 * Created by yao_han on 2015/11/24.
 */
public interface ActionCallbackListener<T> {
    /**
     * 成功时回调
     *
     * @param data
     */
    public void onSuccess(T data);

    /**
     * 失败时回调
     *
     * @param message
     */
    public void onFailure(String message);
}
