package com.shuoye.video.utils.network.converter;

import com.alibaba.fastjson.JSON;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * TODO
 *
 **/
final class FastJsonResponseBodyConvert<T> implements Converter<ResponseBody, T> {

    private final Type type;

    public FastJsonResponseBodyConvert(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        return JSON.parseObject(value.string(), type);
    }
}