package com.bmapleaf.network;

import android.content.Context;

import okhttp3.ResponseBody;

/**
 * Created by ZhangMing on 2017/06/05.
 */

public class MyPresenter extends BasePresenter<IMyView> implements IMyPresenter {
    private INetworkModel networkModel;

    public MyPresenter(Context context, IMyView view) {
        super(context, view);
        networkModel = new NetworkModel();
    }

    @Override
    public void testApi2(String var1, String var2) {
        networkModel.testApi2(var1, var2)
                .request(new NetworkListener<Result>() {
                    @Override
                    public void onSuccess(Result result) {
                        super.onSuccess(result);
                        mView.testOK();
                    }
                });
    }

    @Override
    public void download(String url) {
        networkModel.downloadFile(url, new ProgressListener() {
            @Override
            public void onProgress(long progress, long total, boolean done) {

            }
        }).request(new NetworkListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                super.onSuccess(responseBody);
                //write data responseBody.byteStream()
            }
        });
    }
}
