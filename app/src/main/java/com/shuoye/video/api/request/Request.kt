package com.shuoye.video.api.request

import androidx.lifecycle.LiveData
import com.shuoye.video.api.response.ApiResponse
import com.shuoye.video.database.pojo.*
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

    @GET("banner/banner_data")
    suspend fun getBanner(): ApiResponse<List<Banner>>

    @GET("banner/banner_data")
    fun getBannerTest(): LiveData<ApiResponse<List<Banner>>>

    @GET("home/time_line")
    suspend fun getTimeLine(@Query("wd") wd: Int): ApiResponse<List<TimeLine>>

    @GET("home/recent_updates")
    suspend fun getRecentUpdates(): ApiResponse<List<RecentUpdates>>

    @GET("home/recommended_daily")
    suspend fun getRecommendedDaily(): ApiResponse<List<RecommendedDaily>>

    @GET("anime/info")
    fun getAnimeInfo(@Query("id") id: Int): LiveData<ApiResponse<AnimeInfo>>

}