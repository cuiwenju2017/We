package com.cwj.love_lhh.http;

import com.cwj.love_lhh.base.BaseBean;
import com.cwj.love_lhh.bean.JokesBean;
import com.cwj.love_lhh.bean.NewsDetailsBean;
import com.cwj.love_lhh.bean.NewsListBean;
import com.cwj.love_lhh.bean.RecommendBean;
import com.cwj.love_lhh.bean.RubbishBean;
import com.cwj.love_lhh.bean.TypesBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

        //每日精美语句
        @Headers({
                "app_id:fimtnlhpoltt1v9i",
                "app_secret:Y0hCWis3bTdxbVgzZGZPZ28yWkpkdz09"
        })
        @GET("api/daily_word/recommend")
        Observable<RecommendBean> recommend(@Query("count") String count);

        //获取所有新闻类型列表
        @Headers({
                "app_id:fimtnlhpoltt1v9i",
                "app_secret:Y0hCWis3bTdxbVgzZGZPZ28yWkpkdz09"
        })
        @GET("api/news/types")
        Observable<TypesBean> types();

        //根据新闻类型获取新闻列表
        @Headers({
                "app_id:fimtnlhpoltt1v9i",
                "app_secret:Y0hCWis3bTdxbVgzZGZPZ28yWkpkdz09"
        })
        @GET("api/news/list")
        Observable<NewsListBean> newsList(@Query("typeId") Integer typeId, @Query("page") Integer page);

        //根据新闻id获取新闻详情
        @Headers({
                "app_id:fimtnlhpoltt1v9i",
                "app_secret:Y0hCWis3bTdxbVgzZGZPZ28yWkpkdz09"
        })
        @GET("api/news/details")
        Observable<BaseBean<NewsDetailsBean>> newsDetails(@Query("newsId") String newsId);
    }
}
