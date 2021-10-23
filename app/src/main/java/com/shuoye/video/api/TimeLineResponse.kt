package com.shuoye.video.api

import com.shuoye.video.database.pojo.TimeLine


/**
 * 新番时间表响应体
 * @program video
 * @ClassName TimeLineResponse
 * @author shuoye
 * @create 2021-10-21 20:45
 **/
data class TimeLineResponse(
    val code: Int,
    val data: List<TimeLine> = emptyList(),
    val msg: String
) {
}