package com.shuoye.video.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


/**
 * TODO
 * @program Video
 * @ClassName NetWorkManager
 * @author shuoye
 * @create 2021-10-23 16:10
 **/
class NetWorkManager {
    companion object {
        //单例实例化
        @Volatile
        private var instance: NetWorkManager? = null
        private var retrofit: Retrofit? = null
        private var request: Request? = null

        fun getInstance(): NetWorkManager {
            return instance ?: synchronized(this) {
                instance ?: NetWorkManager()
                    .also { instance = it }
            }
        }
    }

    private fun init() {
        // 初始化okhttp
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            //日志
            .addInterceptor(logger)
            //链接超时为60秒，单位为秒
            .connectTimeout(60, TimeUnit.SECONDS)
            //写入超时
            .writeTimeout(60, TimeUnit.SECONDS)
            //读取超时
            .readTimeout(60, TimeUnit.SECONDS)
            //连接失败重试
            .retryOnConnectionFailure(true)
            .build()

        // 初始化 Retrofit
        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(Request.HOST)
            //支持返回原始String
            .addConverterFactory(ScalarsConverterFactory.create())
            //支持返回FastJson解析后的实体类
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun create(): Request {
        retrofit ?: init()
        synchronized(this) {
            request = retrofit?.create(Request::class.java)
        }
        return request!!
    }
}