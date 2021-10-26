package com.shuoye.video.api.converter

import com.alibaba.fastjson.serializer.SerializeConfig
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/**
 * FastJson转换服务器返回数据
 *
 * @author shuoye
 */
class FastJsonConverterFactory private constructor(serializeConfig: SerializeConfig?) :
    Converter.Factory() {
    private val serializeConfig: SerializeConfig

    /**
     * 需要重写父类中responseBodyConverter，该方法用来转换服务器返回数据
     */
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        return FastJsonRequestBodyConverter<Any>(serializeConfig)
    }

    /**
     * 需要重写父类中responseBodyConverter，该方法用来转换发送给服务器的数据
     */
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return FastJsonResponseBodyConvert<Any>(type)
    }

    companion object {
        @JvmOverloads
        fun create(serializeConfig: SerializeConfig? = SerializeConfig.getGlobalInstance()): FastJsonConverterFactory {
            return FastJsonConverterFactory(serializeConfig)
        }
    }

    init {
        if (serializeConfig == null) {
            throw NullPointerException("serializeConfig == null")
        }
        this.serializeConfig = serializeConfig
    }
}
