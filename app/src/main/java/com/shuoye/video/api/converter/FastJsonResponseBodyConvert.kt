package com.shuoye.video.api.converter

import com.alibaba.fastjson.JSON
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.Type


/**
 * 转换发送给服务器的数据
 *
 * @author shuoye
 */
internal class FastJsonResponseBodyConvert<T>(
    private val type: Type
) : Converter<ResponseBody, T> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        return JSON.parseObject(value.string(), type)
    }
}