package com.bmapleaf.network;

import okhttp3.ResponseBody;

/**
 * Created by ZhangMing on 2016/11/21.
 */

public interface INetworkModel {
    INetworkController<ResponseBody> downloadFile(String url, ProgressListener listener);

    INetworkController<Result> testApi2(String var1, String var2);
}
