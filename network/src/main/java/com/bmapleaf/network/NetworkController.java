package com.bmapleaf.network;

import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangMing on 2017/05/04.
 */

class NetworkController<T> extends Subscriber<T> implements INetworkController<T> {
    private final String TAG = getClass().getSimpleName();
    protected INetworkListener<T> listener;
    protected Observable<T> observable;

    public NetworkController(@NonNull Observable<T> observable) {
        this.observable = observable;
    }

    protected final boolean hasListener() {
        return null != listener;
    }

    protected Scheduler getObserveScheduler() {
        return AndroidSchedulers.mainThread();
    }

    protected Scheduler getSubscribeScheduler() {
        return Schedulers.io();
    }

    @Override
    public final void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (hasListener()) {
            String msg;
            String code = null;
            msg = e.getMessage();
            if (e instanceof HttpException) {
                code = String.valueOf(((HttpException) e).code());
                msg = ((HttpException) e).message();
            } else if (e instanceof UnknownHostException) {
                code = N.error.UnknownHostException;
                msg = NetworkModel.NetDef.currentHost;
            } else if (e instanceof SocketTimeoutException) {
                code = N.error.SocketTimeoutException;
                msg = NetworkModel.NetDef.currentHost;
            }
            listener.onCompleted();
            listener.onFailure(code, msg);
        }
    }

    @Override
    public void onNext(T t) {
        if (hasListener()) {
            listener.onCompleted();
            listener.onSuccess(t);
        }
    }

    @Override
    public final void request(INetworkListener<T> listener) {
        this.listener = listener;
        if (hasListener() && this.listener instanceof INetworkControllerListener) {
            ((INetworkControllerListener) this.listener).onController(this);
        }
        if (null != observable) {
            if (hasListener()) {
                this.listener.onRequest();
            }
            observable.observeOn(getObserveScheduler())
                    .subscribeOn(getSubscribeScheduler())
                    .subscribe(this);
        }
    }

    @Override
    public void retry() {
        NetworkController<T> controller = null;
        try {
            controller = getClass().getConstructor(observable.getClass()).newInstance(observable);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (null != controller) {
                controller.request(listener);
            }
        }
    }

    @Override
    public final void cancel() {
        if (!isUnsubscribed()) {
            if (hasListener()) {
                listener.onCompleted();
            }
            unsubscribe();
            Log.d(TAG, "request canceled.");
        }
    }
}
