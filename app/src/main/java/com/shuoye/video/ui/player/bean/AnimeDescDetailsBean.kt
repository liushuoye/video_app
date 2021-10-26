package com.shuoye.video.ui.player.bean

import java.io.Serializable

/**
 * TODO
 * @program Video
 * @ClassName AnimeDescDetailsBean
 * @author shuoye
 * @create 2021-10-26 14:06
 **/
data class AnimeDescDetailsBean(
    // 标题
    var title: String,
    // 链接
    var url: String,
    // 是否选中
    var selected: Boolean
) : Serializable
