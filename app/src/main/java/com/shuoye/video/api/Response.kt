package com.shuoye.video.api

/**
 * 响应体
 * @program Video
 * @ClassName Response
 * @author shuoye
 * @create 2021-10-23 15:48
 **/
data class Response<T>(
    val code: Int,
    val data: List<T> = emptyList(),
    val msg: String,
    val page: Int
)
