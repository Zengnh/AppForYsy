package com.rootlibs.retrofitpack;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class ToolRetrofit {

    public interface MyGetServer{
        //baseURL(从头开始到任意一个斜杠结束)
        String baseURL="https://www.wanandroid.com/article/list/0/";
        @GET("json?cid=60")
        Call<ResponseBody> getData();
    }

    public void getRequest(){
        //获取Retrofit对象
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MyGetServer.baseURL).build();
//通过Retrofit获取接口服务对象
        MyGetServer server = retrofit.create(MyGetServer.class);
//接口对象调用其方法获取call对象
        Call<ResponseBody> data = server.getData();
//call执行请求
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
//                    Log.e(TAG, "onFailure: " +  response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    public interface MyServer {
        public static  String URL = "http://apicloud.mob.com/appstore/health/";//必须以反斜杠结尾
        //POST("search?")    POST("search")相同
//@Field("key") String value post请求用来提交参数的
        //@FormUrlEncoded post请求提交form表单的时候如果有参数,需要填加这个注解,用来将提交的参数编码
        //post请求不提交参数,不要加,
        //如果有提交的参数,没有加@FormUrlEncoded
        //@Field和@FieldMap一样，@FieldMap只不过是把一个一个的参数,合成一个map
        @POST("search?")
        @FormUrlEncoded
        Call<ResponseBody> postData1(@Field("key") String appcKey, @Field("name") String appKey);
        @POST("search")
        @FormUrlEncoded
        Call<ResponseBody> postData2(@FieldMap Map<String,Object> map);


        @Headers({"token:one","content2:two"})
        @POST("search")
        @FormUrlEncoded
        Call<ResponseBody> postDataLogin(@Header("head") String value, @FieldMap Map<String,Object> map);

    }
    //POST异步
    private void initPostEnqueue() {
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyServer.URL)
                .build();
        //2.获取MyServer接口服务对象
        MyServer myServer = retrofit.create(MyServer.class);
        //3.获取Call对象
        //方式一
//        Call<ResponseBody> call1 = myServer.postData1("908ca46881994ffaa6ca20b31755b675");
        //方式二
        //不用切换主线程了,因为Retrofit帮我们切过了
        //okHttpClient需要自己切换主线程
        Map<String,Object> map = new HashMap<>();
        map.put("appKey","908ca46881994ffaa6ca20b31755b675");
        Call<ResponseBody> call = myServer.postData2(map);
        //4.Call对象执行请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    Log.e("retrofit", "onResponse: "+result);
//                    tv.setText(result);//默认直接回调主线程
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("retrofit", "onFailure: "+t.getMessage());
            }
        });
    }
}
