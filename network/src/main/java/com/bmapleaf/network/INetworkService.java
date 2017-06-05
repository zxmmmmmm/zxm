package com.bmapleaf.network;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by zxm on 2017/06/05.
 */

interface INetworkService {
    @FormUrlEncoded
    @POST(N.url.network_test)
    Observable<Result> testApi1(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(N.url.network_test)
    Observable<Result> testApi2(@QueryMap Map<String, Object> params1, @FieldMap Map<String, Object> params);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);
}
