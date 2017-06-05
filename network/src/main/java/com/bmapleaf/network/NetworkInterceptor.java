package com.bmapleaf.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * Created by zxm on 2017/05/15.
 */

public class NetworkInterceptor implements Interceptor {
    private Logger logger;

    public NetworkInterceptor() {
        this(Logger.DEFAULT);
    }

    public NetworkInterceptor(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (null != logger) {
            logger.logHost(request.url().host());
        }
        return chain.proceed(request);
    }

    public interface Logger {
        /**
         * A {@link NetworkInterceptor.Logger} defaults output appropriate for the current platform.
         */
        Logger DEFAULT = new Logger() {
            @Override
            public void logHost(String host) {
                Platform.get().log(INFO, host, null);
            }
        };

        void logHost(String host);
    }
}
