package com.rootlibs.retrofitpack;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface ApiService {
    @FormUrlEncoded
    @POST("api/user/loginn")
    Call<BaseResponse<UserInfo>> login(@FieldMap Map<String, String> map);
}