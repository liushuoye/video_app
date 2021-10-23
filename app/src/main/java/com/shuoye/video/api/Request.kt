package com.shuoye.video.api

import com.shuoye.video.database.pojo.Banner
import com.shuoye.video.database.pojo.TimeLine
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 请求
 * @program Video
 * @ClassName Request
 * @author shuoye
 * @create 2021-10-23 16:27
 **/
interface Request {
    companion object {
        const val HOST = "http://47.106.154.121:8080/video/api/"
    }

    @GET("anime/time_line")
    suspend fun getTimeLine(@Query("wd") wd: Int): Response<TimeLine>

    @GET("banner/banner_data")
    suspend fun getBanner(): Response<Banner>
}