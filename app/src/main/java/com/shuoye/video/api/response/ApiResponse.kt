package com.shuoye.video.api.response

/**
 * 响应体
 * @program Video
 * @ClassName ApiResponse
 * @author shuoye
 * @create 2021-10-23 15:48
 **/
data class ApiResponse<T>(
    val code: Int?,
    val data: T,
    val msg: String,
)
