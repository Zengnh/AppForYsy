package com.rootlibs.retrofitpack;

import com.jsyllibs.tool_gson.GsonUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitManager {
    private volatile static RetrofitManager instance;
    private String rootUrl="http:192.169.2.193:8082";
    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    private RetrofitManager() {
        buildRetrofit();
    }

    private void buildRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(true);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();

//.addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(ToolAddress.getInstance().getBase())
        sRetrofitLog = new Retrofit.Builder()
                .baseUrl(rootUrl)
                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(LenientGsonConverterFactory.create(GsonUtils.INSTANCE.getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private Retrofit sRetrofitLog;
    private static final int DEFAULT_TIMEOUT = 15;

    public Retrofit getRetrofit() {
        return sRetrofitLog;
    }

}