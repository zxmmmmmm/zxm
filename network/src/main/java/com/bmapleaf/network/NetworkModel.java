package com.bmapleaf.network;

import android.util.Log;

import java.util.HashMap;

import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zxm on 2016/11/21.
 */

public class NetworkModel implements INetworkModel, HttpLoggingInterceptor.Logger, NetworkInterceptor.Logger {
    private static final String TAG = "NetworkModel";
    private INetworkService mNetworkService;

    public NetworkModel() {
        mNetworkService = new NetworkServiceBuilder()
                .setBaseUrl(N.url.base)
                .setNetworkInterceptor(this)
                .setHttpLoggingInterceptor(this)
                .build(INetworkService.class);
    }

    private INetworkService getNetworkService() {
        return mNetworkService;
    }


    @Override
    public void log(String message) {
        Log.d(TAG, message);
    }

    @Override
    public void logHost(String host) {
        //Log.d(TAG, "currentHost: " + host);
        NetDef.currentHost = host;
    }

    @Override
    public INetworkController<ResponseBody> downloadFile(String url, ProgressListener listener) {
//        INetworkService downloadService = new NetworkServiceBuilder()
//                .setBaseUrl(N.url.base)
//                .setNetworkInterceptor(NetworkModel.this)
//                .setProgressListener(listener)
//                .build(INetworkService.class);
//        return new NetworkController<>(downloadService.downloadFile(url));
        return new NetworkController<>(getNetworkService().downloadFile(url));
    }

    @Override
    public INetworkController<Result> testApi2(final String var1, String var2) {
//        return new NetworkController<>(getNetworkService().testApi1(
//                new HashMap<String, Object>() {
//                    {
//                        put("var1", var1);
//                    }
//                }));
        return new NetworkController<>(getNetworkService().testApi2(
                new HashMap<String, Object>() {
                    {
                        put("var1", var1);
                    }
                },
                new HashMap<String, Object>() {
                    {
                        put("var2", var1);
                    }
                }));
    }

    static class NetDef {
        static String currentHost;
    }
}
