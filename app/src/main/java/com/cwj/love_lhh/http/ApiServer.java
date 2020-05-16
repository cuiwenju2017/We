package com.cwj.love_lhh.http;

import com.cwj.love_lhh.bean.LatestBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServer {

    //检测版本更新
    @GET("apps/latest/{id}")
    Call<ResponseBody> latest(@Path("id") String id, @Query("api_token") String api_token);
}
