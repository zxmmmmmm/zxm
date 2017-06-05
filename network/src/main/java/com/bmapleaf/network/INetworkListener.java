package com.bmapleaf.network;

/**
 * Created by ZhangMing on 2016/11/18.
 */

public interface INetworkListener<T> {
    void onFailure(String errCode, String errMsg);

    void onSuccess(T t);

    void onRetry();

    /**
     * called while request begin
     */
    void onRequest();

    /**
     * called while request end
     */
    void onCompleted();
}
