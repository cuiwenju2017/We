package com.cwj.we.http;

import com.cwj.we.bean.LatestBean;
import com.cwj.we.bean.MsgBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class API {

    public static final String BASE_URL_UPDATA = "http://api.bq04.com/";
    public static final String BASE_URL_Qingyunke = "http://api.qingyunke.com/";

    public interface UPDATAApi {
        //检测版本更新
        @GET("apps/latest/{id}")
        Observable<LatestBean> latest(@Path("id") String id, @Query("api_token") String api_token);
    }

    public interface QingyunkeApi {
        //智能机器人
        @GET("api.php?key=free&appid=0")
        Observable<MsgBean> msg(@Query("msg") String msg);
    }
}
