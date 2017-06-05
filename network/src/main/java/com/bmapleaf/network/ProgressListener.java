package com.bmapleaf.network;

/**
 * Created by zxm on 2016/11/28.
 */

public interface ProgressListener {
    void onProgress(long progress, long total, boolean done);
}
