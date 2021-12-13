package com.dongyuwuye.component_net;


import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by MC on 2017/12/19.
 * Retrofit管理类
 */

public class RetrofitManager {
    private static RetrofitManager retrofitManager = null;
    private Retrofit retrofit = null;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private RetrofitManager() {

    }

    public static RetrofitManager getInstance() {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
        }
        return retrofitManager;
    }

    /**
     * 网络工具初始化
     * Application中调用
     */
    public void init(String base_url, long time, Interceptor... interceptors) {
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .client(createOkHttpClient(time, interceptors))
                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient createOkHttpClient(long time, Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(time, TimeUnit.SECONDS)
                .writeTimeout(time, TimeUnit.SECONDS)
                .readTimeout(time, TimeUnit.SECONDS);
        for (Interceptor item : interceptors) {
            builder.addInterceptor(item);
        }
        return builder
                .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .build();
    }


    /**
     * base_Url
     */
//    private Interceptor addHeader() {
//        return new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                //获取原始的originalRequest
//                Request originalRequest = chain.request();
//                TokenResp tokenResp = UserDaoUtils.getToken();
//                if (tokenResp != null) {//已登录
//                    Request request = originalRequest.newBuilder()
//                            .header("scal", tokenResp.getScal())
//                            .header("phone", tokenResp.getPhone())
//                            .build();
//                    return chain.proceed(request);
//                } else {
//                    return chain.proceed(originalRequest);
//                }
//            }
//        };
//    }


}
