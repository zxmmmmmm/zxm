package com.bmapleaf.network;

/**
 * Created by ZhangMing on 2016/11/21.
 */

public interface IView {
    void onSuccess();

    void onFailure(String errMsg);

    /**
     * called while request begin
     */
    void onRequest();

    /**
     * called while request end
     */
    void onCompleted();
}
