package com.shuoye.video.database.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alibaba.fastjson.annotation.JSONField
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * TODO
 * @program Video
 * @ClassName Player
 * @author shuoye
 * @create 2021-10-26 14:06
 **/
@Entity(tableName = "player")
data class Player(
    @JSONField(name = "ID")
    @SerializedName("ID")
    val id: Int,
    val animeInfoId: Int,
    @SerializedName("Title")
    @JSONField(name = "Title")
    val title: String?,
    @SerializedName("Title_l")
    @JSONField(name = "Title_l")
    val titleL: String?,
    @SerializedName("PlayId")
    @JSONField(name = "PlayId")
    val playId: String?,
    @SerializedName("PlayVid")
    @JSONField(name = "PlayVid")
    @PrimaryKey(autoGenerate = false)
    val playVid: String,
    @SerializedName("EpName")
    @JSONField(name = "EpName")
    val epName: String? = null,
    @SerializedName("EpPic")
    @JSONField(name = "EpPic")
    val epPic: String? = null,
    val ex: String? = null,
    val url: String? = null,
    // 是否选中
    var selected: Boolean
) : Serializable