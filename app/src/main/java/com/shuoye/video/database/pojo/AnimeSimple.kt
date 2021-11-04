package com.shuoye.video.database.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alibaba.fastjson.annotation.JSONField
import com.google.gson.annotations.SerializedName


/**
 * TODO
 * @program Video
 * @ClassName AnimeSimple
 * @author shuoye
 * @create 2021-11-04 13:05
 **/
data class AnimeSimple(
    @JSONField(name = "AID")
    @SerializedName("AID")
    @PrimaryKey
    val id: Int,
    @JSONField(name = "NewTitle")
    @SerializedName("NewTitle")
    val newTitle: String,

    @JSONField(name = "PicSmall")
    @SerializedName("PicSmall")
    val picSmall: String,

    @JSONField(name = "Title")
    @SerializedName("Title")
    val title: String,

    @JSONField(name = "CCnt")
    @SerializedName("CCnt")
    val rankCount: Int,
)

@Entity(tableName = "anime_update")
data class Update(
    @PrimaryKey(autoGenerate = true)
    val uuid: Int,

    @JSONField(name = "AID")
    @SerializedName("AID")
    val id: Int,

    @JSONField(name = "NewTitle")
    @SerializedName("NewTitle")
    val newTitle: String,

    @JSONField(name = "PicSmall")
    @SerializedName("PicSmall")
    val picSmall: String,

    @JSONField(name = "Title")
    @SerializedName("Title")
    val title: String,
)

@Entity(tableName = "anime_recommend")
data class Recommend(
    @PrimaryKey(autoGenerate = true)
    val uuid: Int,

    @JSONField(name = "AID")
    @SerializedName("AID")
    val id: Int,

    @JSONField(name = "NewTitle")
    @SerializedName("NewTitle")
    val newTitle: String,

    @JSONField(name = "PicSmall")
    @SerializedName("PicSmall")
    val picSmall: String,

    @JSONField(name = "Title")
    @SerializedName("Title")
    val title: String,
)

@Entity(tableName = "anime_search")
data class Search(
    @PrimaryKey
    @SerializedName("AID")
    val id: Int,

    @SerializedName("R动画种类")
    val genre: String?,

    @SerializedName("R动画名称")
    val name: String,


    @SerializedName("R原版名称")
    val originalName: String?,

    @SerializedName("R原作")
    val originalAuthor: String?,

    @SerializedName("R制作公司")
    val productionCompany: String?,


    @SerializedName("R首播时间")
    val premiereTime: String?,

    @SerializedName("R播放状态")
    val status: String?,

    @SerializedName("R资源类型")
    val resource: String?,


    @SerializedName("R新番标题")
    val newTitle: String?,

    @SerializedName("R封面图小")
    val coverSmall: String?,

    @SerializedName("R简介")
    val introduction: String?,

    val labels: String
)