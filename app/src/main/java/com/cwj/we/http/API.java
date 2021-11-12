package com.cwj.we.http;

import com.cwj.we.bean.LatestBean;
import com.cwj.we.bean.MsgBean;
import com.tencent.mmkv.MMKV;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class API {

    public static final String BASE_URL_UPDATA = "http://api.bq04.com/";
    public static final String BASE_URL_Qingyunke = "http://api.qingyunke.com/";
    public static final MMKV kv = MMKV.defaultMMKV();
    public static final String vip1 = "https://www.administratorw.com/index/qqvod.php?url=";
    public static final String vip2 = "https://www.administratorw.com/video.php?url=";
    public static final String vip3 = "http://www.82190555.com/video.php?url=";
    public static final String vip4 = "http://www.sfsft.com/video.php?url=";

    public interface Api {
        //检测版本更新
        @GET("apps/latest/{id}")
        Observable<LatestBean> latest(@Path("id") String id, @Query("api_token") String api_token);

        //智能机器人
        @GET(BASE_URL_Qingyunke + "api.php?key=free&appid=0")
        Observable<MsgBean> msg(@Query("msg") String msg);
    }
}
