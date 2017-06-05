package com.bmapleaf.network;

import android.support.annotation.Nullable;

/**
 * Created by ZhangMing on 2017/05/04.
 */

public interface INetworkController<T> {
    void request(@Nullable INetworkListener<T> listener);

    void retry();

    void cancel();
}
