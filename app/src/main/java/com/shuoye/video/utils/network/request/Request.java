package com.shuoye.video.utils.network.request;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.Map;

/**
 * 封装请求的接口
 *
 * @program: WebCrawlerServer
 * @ClassName Request
 * @author: shuoye
 * @create: 2021-07-06 19:08
 **/
public interface Request {
    String HOST = "http://sexinsex.net/";


    @GET("user")
    Observable<Response<String>> getList();

    @GET
    Observable<String> getHtml(@Url String url);

    @GET
    Observable<String> getHtml(@Url String url, @HeaderMap Map<String, String> headers);

    @GET
    Observable<String> getHtml(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> map);

    @GET
    Observable<JSONArray> getJSONArray(@Url String url);

    @GET
    Observable<JSONObject> getJSONObject(@Url String url);

    @GET
    Observable<JSONObject> getJSONObject(@Url String url, @HeaderMap Map<String, String> headers);

    @POST
    Observable<String> postHtml(@Url String url);

    @FormUrlEncoded
    @POST
    Observable<String> postHtml(@Url String url, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
    Observable<String> postHtml(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> map);

    /**
     * 上传json格式数据
     *
     * @param json 上传json格式数据，直接传入实体它会自动转为json，这个转化方式是GsonConverterFactory定义的
     * @return String
     */
    @POST
    Observable<String> postUploadJson(@Body RequestBody json);

    @GET("http://sexinsex.net/bbs/forum-110-1.html")
    Observable<String> getSexinSex();
}
