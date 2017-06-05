package com.bmapleaf.network;

import android.content.Context;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by ZhangMing on 2016/11/21.
 */

public class BasePresenter<T extends IView> implements IBasePresenter {
    protected final String TAG = this.getClass().getSimpleName();
    protected T mView;
    private Context mContext;
    private INetworkController mNetworkController;

    public BasePresenter(Context context, T view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void retry() {
        if (null != mNetworkController) {
            mNetworkController.retry();
        }
    }

    @Override
    public void cancel() {
        if (null != mNetworkController) {
            mNetworkController.cancel();
        }
    }

    protected final String mergeError(String errCode, String errMsg) {
        /*fix error msg & error code*/
        if (null == errMsg) {
            errMsg = "";
        }
        if (null == errCode) {
            errCode = "";
        }
        /*find error string*/
        if (!errCode.isEmpty()) {
            String msg;
            if (null != (msg = queryString(errCode)) && !msg.isEmpty()) {
                if (errCode.equals("K100") || errCode.equals("K101")) {
                    errMsg = String.format(msg, errMsg);
                } else {
                    errMsg = msg;
                }
            }
        }
        return String.format(mContext.getString(R.string.error_format), errCode, errMsg);
    }

    protected final String queryString(String errCode) {
        String msg = null;
        try {
            msg = XMLMap.getValueByKey(mContext.getResources(), R.xml.error, errCode);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    protected class NetworkListener<V> implements INetworkListener<V>, INetworkControllerListener {
        @Override
        public void onFailure(String errCode, String errMsg) {
            if (null != mView) {
                mView.onFailure(mergeError(errCode, errMsg));
            }
        }

        @Override
        public final void onController(INetworkController controller) {
            mNetworkController = controller;
        }

        @Override
        public void onSuccess(V v) {
            if (null != mView) {
                mView.onSuccess();
            }
        }

        @Override
        public void onRetry() {

        }

        @Override
        public final void onRequest() {
            mView.onRequest();
        }

        @Override
        public final void onCompleted() {
            mView.onCompleted();
        }
    }
}
