package com.shuoye.video.utils.network.interceptor;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 响应编码拦截器
 **/
public class EncodingInterceptor implements Interceptor {

    /**
     * 自定义编码
     */
    private final String encoding;

    public EncodingInterceptor(String encoding) {
        this.encoding = encoding;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NotNull
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        settingClientCustomEncoding(response);
        return response;
    }

    /**
     * 当服务器不返回编码时，设置客户端自定义编码
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void settingClientCustomEncoding(Response response) throws IOException {
//        setHeaderContentType(response);
        setBodyContentType(response);
    }

    /**
     * 在标题中设置contentType
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setHeaderContentType(Response response) throws IOException {
        String contentType = response.header("Content-Type");
        if (StringUtils.isNotBlank(contentType) && contentType.contains("charset")) {
            return;
        }
        // build new headers
        Headers headers = response.headers();
        Builder builder = headers.newBuilder();
        builder.removeAll("Content-Type");
        builder.add("Content-Type", (StringUtils.isNotBlank(contentType) ? contentType + "; " : "") + "charset=" + encoding);
        headers = builder.build();
        // setting headers using reflect
        Class<Response> _response = Response.class;
        try {
            Field field = _response.getDeclaredField("headers");
            field.setAccessible(true);
            field.set(response, headers);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IOException("使用反射设置头发生错误", e);
        }
    }

    /**
     * 设置正文contentType
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setBodyContentType(Response response) throws IOException {
        ResponseBody body = response.body();
        // 使用reflect设置主体contentTypeString
        assert body != null;
        Class<? extends ResponseBody> aClass = body.getClass();
        try {
            Field field = aClass.getDeclaredField("contentTypeString");
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            String contentTypeString = String.valueOf(field.get(body));
            if (StringUtils.isNotBlank(contentTypeString) && contentTypeString.contains("charset")) {
                return;
            }
            field.set(body, (StringUtils.isNotBlank(contentTypeString) ? contentTypeString + "; " : "") + "charset=" + encoding);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IOException("使用反射设置头发生错误", e);
        }
    }
}
