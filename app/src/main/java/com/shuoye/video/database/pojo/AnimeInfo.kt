package com.shuoye.video.database.pojo

import androidx.room.*


/**
 * TODO
 * @program Video
 * @ClassName AnimeInfo
 * @author shuoye
 * @create 2021-10-23 17:13
 **/
@Entity(tableName = "anime_info")
data class AnimeInfo(
    @PrimaryKey
    val id: Int,

    /**
     * 地区
     */
    val region: String?,

    /**
     * 动画种类
     */
    val genre: String?,

    /**
     * 动画名称
     */
    val name: String?,

    /**
     * 原版名称
     */
    val originalName: String?,

    /**
     * 其他名称
     */
    val otherNames: String?,

    /**
     * 字母索引
     */
    val letter: String?,

    /**
     * 原作
     */
    val originalAuthor: String?,

    /**
     * 制作公司
     */
    val productionCompany: String?,

    /**
     * 首播时间
     */
    val premiereTime: String?,

    /**
     * 播放状态
     */
    val status: String?,

    /**
     * 视频清晰度
     */
    val videoResolution: String?,

    /**
     * 资源类型
     */
    val resource: String?,

    /**
     * 新番标题
     */
    val newTitle: String?,

    /**
     * 更新时间
     */
    val updateTime: Long?,

    /**
     * 推荐星级
     */
    val recommendStar: Int?,

    /**
     * 封面
     */
    val cover: String?,

    /**
     * 封面图小
     */
    val coverSmall: String?,

    /**
     * 简介
     */
    val introduction: String?,

    /**
     * 系列名
     */
    val seriesName: String?,


    /**
     * 官方网站
     */
    val officialWebsite: String?,

    /**
     * 网盘资源
     */
    val networkDisk: String?,

    /**
     * 首播年份
     */
    val year: String?,

    /**
     * 首播季度
     */
    val season: String?,

    /**
     * 热度
     */
    val rankCount: Int?,

    /**
     * 收藏数
     */
    val collectCount: Int?,

    /**
     * 评论数
     */
    val commentCount: Int?,

    /**
     * 播放链接
     */
    val players: String?,
)

