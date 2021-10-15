package com.shuoye.video.utils.network.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Converter;

final class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    private final SerializeConfig serializeConfig;

    public FastJsonRequestBodyConverter(SerializeConfig serializeConfig) {
        this.serializeConfig = serializeConfig;
    }

    @Override
    public RequestBody convert(@NotNull T value) {
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value, serializeConfig));
    }
}
