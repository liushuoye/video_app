package com.shuoye.video.utils.network.converter;

import com.alibaba.fastjson.serializer.SerializeConfig;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * TODO
 *
 **/
public class FastJsonConverterFactory extends Converter.Factory {
    private final SerializeConfig serializeConfig;

    private FastJsonConverterFactory(SerializeConfig serializeConfig) {
        if (serializeConfig == null)
            throw new NullPointerException("serializeConfig == null");
        this.serializeConfig = serializeConfig;
    }

    public static FastJsonConverterFactory create() {
        return create(SerializeConfig.getGlobalInstance());
    }

    public static FastJsonConverterFactory create(SerializeConfig serializeConfig) {
        return new FastJsonConverterFactory(serializeConfig);
    }

    /**
     * 需要重写父类中responseBodyConverter，该方法用来转换服务器返回数据
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(@NotNull Type type, @NotNull Annotation[] parameterAnnotations, @NotNull Annotation[] methodAnnotations, @NotNull Retrofit retrofit) {
        return new FastJsonRequestBodyConverter<>(serializeConfig);
    }

    /**
     * 需要重写父类中responseBodyConverter，该方法用来转换发送给服务器的数据
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NotNull Type type, @NotNull Annotation[] annotations, @NotNull Retrofit retrofit) {
        return new FastJsonResponseBodyConvert<>(type);
    }
}
