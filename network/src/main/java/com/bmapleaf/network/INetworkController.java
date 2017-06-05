package com.bmapleaf.network;

import android.support.annotation.Nullable;

/**
 * Created by zxm on 2017/05/04.
 */

public interface INetworkController<T> {
    void request(@Nullable INetworkListener<T> listener);

    void retry();

    void cancel();
}
