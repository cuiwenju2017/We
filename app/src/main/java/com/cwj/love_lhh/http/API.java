package com.cwj.love_lhh.http;

import android.database.Observable;

import com.cwj.love_lhh.base.BaseBean;
import com.cwj.love_lhh.bean.LatestBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Description : API
 * 接口的管理类
 *
 * @author XuCanyou666
 * @date 2020/2/7
 */


public class API {

    static final String BASE_URL = "http://api.bq04.com/";

    public interface WAZApi {

        // 版本查询
        @GET("apps/latest/{id}")
        Observable<LatestBean> getLatest(@Path("id") String id, @Query("api_token") String api_token);

    }

}
