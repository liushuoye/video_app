package com.shuoye.video.utils.network.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import lombok.extern.log4j.Log4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存拦截器
 **/
public class CacheInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();//获取请求
        Response response = chain.proceed(request);
        int maxTime = 20;//缓存时间 单位秒
        return response.newBuilder()
                .header("Cache-Control", "public, max-age=" + maxTime)
                .removeHeader("Pragma")
                .build();
    }
}
