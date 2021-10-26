package com.shuoye.video.database.pojo

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


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
    @SerializedName("AID")
    val id: Int,

    /**
     * 地区
     */
    @SerializedName("R地区")
    val region: String?,

    /**
     * 动画种类
     */
    @SerializedName("R动画种类")
    val genre: String?,

    /**
     * 动画名称
     */
    @SerializedName("R动画名称")
    val name: String,

    /**
     * 原版名称
     */
    @SerializedName("R原版名称")
    val originalName: String?,

    /**
     * 其他名称
     */
    @SerializedName("R其它名称")
    val otherNames: String?,

    /**
     * 字母索引
     */
    @SerializedName("R字母索引")
    val letter: String?,

    /**
     * 原作
     */
    @SerializedName("R原作")
    val originalAuthor: String?,

    /**
     * 制作公司
     */
    @SerializedName("R制作公司")
    val productionCompany: String?,

    /**
     * 首播时间
     */
    @SerializedName("R首播时间")
    val premiereTime: String?,

    /**
     * 播放状态
     */
    @SerializedName("R播放状态")
    val status: String?,

    /**
     * 视频清晰度
     */
    @SerializedName("R视频尺寸")
    val videoResolution: String?,

    /**
     * 资源类型
     */
    @SerializedName("R资源类型")
    val resource: String?,

    /**
     * 新番标题
     */
    @SerializedName("R新番标题")
    val newTitle: String?,

    /**
     * 更新时间
     */
    @SerializedName("R更新时间unix")
    val updateTime: Long?,

    /**
     * 推荐星级
     */
    @SerializedName("R推荐星级")
    val recommendStar: Int?,

    /**
     * 封面
     */
    @SerializedName("R封面图")
    val cover: String?,

    /**
     * 封面图小
     */
    @SerializedName("R封面图小")
    val coverSmall: String?,

    /**
     * 简介
     */
    @SerializedName("R简介")
    val introduction: String?,

    /**
     * 系列名
     */
    @SerializedName("R系列")
    val seriesName: String?,


    /**
     * 官方网站
     */
    @SerializedName("R官方网站")
    val officialWebsite: String?,

    /**
     * 网盘资源
     */
    @SerializedName("R网盘资源")
    val networkDisk: String?,

    /**
     * 首播年份
     */
    @SerializedName("R首播年份")
    val year: String?,

    /**
     * 首播季度
     */
    @SerializedName("R首播季度")
    val season: String?,

    /**
     * 热度
     */
    @SerializedName("RankCnt")
    val rankCount: Int?,

    /**
     * 收藏数
     */
    @SerializedName("CollectCnt")
    val collectCount: Int?,

    /**
     * 评论数
     */
    @SerializedName("CommentCnt")
    val commentCount: Int?,


    ) {
    @SerializedName("R在线播放All")
    @Ignore
    val players: List<List<Player>> = emptyList()

    @Ignore
    val series: List<AnimeInfo> = emptyList()

    @Ignore
    val tags: List<Tag> = emptyList()
}

