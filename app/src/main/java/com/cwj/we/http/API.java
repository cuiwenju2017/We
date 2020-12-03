package com.cwj.we.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    String BASE_URL = "https://v1.alapi.cn/";
    String BASE_URL_UPDATA = "http://api.bq04.com/";

    //检测版本更新
    @GET("apps/latest/{id}")
    Call<ResponseBody> latest(@Path("id") String id, @Query("api_token") String api_token);
}
