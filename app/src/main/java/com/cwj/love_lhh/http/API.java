package com.cwj.love_lhh.http;

import com.cwj.love_lhh.base.BaseBean;
import com.cwj.love_lhh.bean.JokesBean;
import com.cwj.love_lhh.bean.LatestBean;
import com.cwj.love_lhh.bean.RubbishBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    static final String BASE_URL = "https://www.mxnzp.com/";

    public interface WAZApi {

        //垃圾分类查询
        @Headers({
                "app_id:fimtnlhpoltt1v9i",
                "app_secret:Y0hCWis3bTdxbVgzZGZPZ28yWkpkdz09"
        })
        @GET("api/rubbish/type")
        Observable<BaseBean<RubbishBean>> rubbish(@Query("name") String name);

        //分页获取笑话段子列表
        @Headers({
                "app_id:fimtnlhpoltt1v9i",
                "app_secret:Y0hCWis3bTdxbVgzZGZPZ28yWkpkdz09"
        })
        @GET("api/jokes/list")
        Observable<BaseBean<JokesBean>> jokes(@Query("page") Integer page);
    }

}
