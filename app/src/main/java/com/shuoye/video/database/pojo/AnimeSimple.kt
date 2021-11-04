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