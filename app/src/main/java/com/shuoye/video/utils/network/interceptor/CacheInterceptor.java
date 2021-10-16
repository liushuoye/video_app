package com.shuoye.video.utils.network.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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
        //获取请求
        Request request = chain.request();
        Response response = chain.proceed(request);
        //缓存时间 单位秒
        int maxTime = 20;
        return response.newBuilder()
                .header("Cache-Control", "public, max-age=" + maxTime)
                .removeHeader("Pragma")
                .build();
    }
}
