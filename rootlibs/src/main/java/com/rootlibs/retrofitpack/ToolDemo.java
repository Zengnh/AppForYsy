package com.rootlibs.retrofitpack;

import android.util.ArrayMap;
import android.util.Log;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToolDemo {
    protected ApiService oIesCenter = RetrofitManager.getInstance().getRetrofit().create(ApiService.class);

    public void test() {
        Map<String, String> login = new ArrayMap<>();
        Call<BaseResponse<UserInfo>> mCall = oIesCenter.login(login);
        mCall.enqueue(new Callback<BaseResponse<UserInfo>>() {
            @Override
            public void onResponse(Call<BaseResponse<UserInfo>> call, Response<BaseResponse<UserInfo>> response) {
                BaseResponse<UserInfo> result = response.body();
                Log.i("jsyl_logreq", "onFailure call is canceled."+result.getCode());

            }

            @Override
            public void onFailure(Call<BaseResponse<UserInfo>> call, Throwable t) {
                Log.i("jsyl_logreq", "onFailure call is canceled." + call.isCanceled() + "  msg:" + t.getMessage());

            }
        });
    }
}
