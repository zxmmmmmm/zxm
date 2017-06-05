package com.bmapleaf.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zxm on 2016/11/16.
 */

class NetworkServiceBuilder {
    private static final String TAG = "NetworkServiceBuilder";
    private NetworkInterceptor.Logger logger1;
    private HttpLoggingInterceptor.Logger logger2;
    private ProgressListener progressListener;
    private String baseUrl;
    private Converter.Factory converterFactory;

    public NetworkServiceBuilder setConverterFactory(Converter.Factory converterFactory) {
        this.converterFactory = converterFactory;
        return this;
    }

    public NetworkServiceBuilder setNetworkInterceptor(NetworkInterceptor.Logger logger) {
        this.logger1 = logger;
        return this;
    }

    public NetworkServiceBuilder setHttpLoggingInterceptor(HttpLoggingInterceptor.Logger logger) {
        this.logger2 = logger;
        return this;
    }

    public NetworkServiceBuilder setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
        return this;
    }

    public NetworkServiceBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public <T> T build(Class<T> service) {
        Log.d(TAG, "buildService: " + service.getSimpleName() + " baseUrl: " + baseUrl);
        OkHttpClient client = null;
        boolean interceptNet = null != logger1;
        boolean interceptLog = null != logger2;
        boolean interceptProgress = null != progressListener;
        if (interceptLog || interceptProgress) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            if (interceptNet) {
                clientBuilder.addInterceptor(new NetworkInterceptor(logger1));
            }
            if (interceptLog) {
                clientBuilder.addInterceptor(new HttpLoggingInterceptor(logger2).setLevel(HttpLoggingInterceptor.Level.BODY));
            }
            if (interceptProgress) {
                clientBuilder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse
                                .newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                });
            }
            client = clientBuilder.build();
        }
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(null == converterFactory ? GsonConverterFactory.create() : converterFactory);
        if (null != client) {
            retrofitBuilder.client(client);
        }
        return retrofitBuilder.build().create(service);
    }
}
