package com.shuoye.video.utils.network.request;

import com.shuoye.video.pojo.BannerData;
import com.shuoye.video.pojo.TimeLine;
import com.shuoye.video.utils.network.response.Response;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 封装请求的接口
 **/
public interface Request {
    String HOST = "http://47.106.154.121:8080/video/api/";

    /**
     * 获取轮播图资源
     *
     * @return Observable<List < DataBean>>
     */
    @GET("banner/banner_data")
    Observable<Response<List<BannerData>>> getDataBeans();

    /**
     * 获取新番时间表资源
     *
     * @return Observable<Map < Integer, List < TimeLine>>>
     */
    @GET("anime/time_line")
    Observable<Response<Map<Integer, List<TimeLine>>>> getTimeLines();


    @GET
    Observable<String> getHtml(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
    Observable<String> postHtml(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> map);

}
