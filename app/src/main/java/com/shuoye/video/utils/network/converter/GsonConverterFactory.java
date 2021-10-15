package com.shuoye.video.utils.network.converter;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * 提供ResponseBody的转换和RequestBody的转换
 **/
public final class GsonConverterFactory extends Converter.Factory {


    public static GsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new GsonConverterFactory(gson);
    }

    private final Gson gson;

    private GsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NotNull Type type, @NotNull Annotation[] annotations,
                                                            @NotNull Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(gson, adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Converter<?, RequestBody> requestBodyConverter(@NotNull Type type, @NotNull Annotation[] parameterAnnotations, @NotNull Annotation[] methodAnnotations, @NotNull Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }
}
