package com.niramaya.niramaya_app.serverapi;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetroApis
{
    @POST("NewLogin")
    Call<ResponseBody> doLogin(@Body RequestBody requestBody);

    @GET("GetUser")
    Call<ResponseBody> getUser();
}
