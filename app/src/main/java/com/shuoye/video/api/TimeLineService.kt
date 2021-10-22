package com.shuoye.video.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 从服务器新番时间表资源
 * @program video
 * @ClassName TimeLineService
 * @author shuoye
 * @create 2021-10-21 20:40
 **/
interface TimeLineService {

    @GET("anime/time_line")
    suspend fun getTimeLine(
        @Query("wd") wd: Int
    ): TimeLineResponse


    companion object {
        private const val BASE_URL = "http://47.106.154.121:8080/video/api/"


        fun create(): TimeLineService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TimeLineService::class.java)
        }
    }
}