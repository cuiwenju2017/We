package com.cwj.we.http;

import com.cwj.we.bean.LatestBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class API {

    public static final String BASE_URL_UPDATA = "http://api.bq04.com/";

    public interface UPDATAApi {
        //检测版本更新
        @GET("apps/latest/{id}")
        Observable<LatestBean> latest(@Path("id") String id, @Query("api_token") String api_token);
    }
}
