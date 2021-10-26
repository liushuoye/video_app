package com.shuoye.video.utils

/**
 * 视频播放工具类
 * @program Video
 * @ClassName VideoUtils
 * @author shuoye
 * @create 2021-10-26 16:01
 **/
object VideoUtils {
    /**
     * 获取链接
     *
     * @param url
     * @return
     */
    fun getSiteUrl(url: String): String {
        return if (url.startsWith("http")) url else "http$url"
    }
}