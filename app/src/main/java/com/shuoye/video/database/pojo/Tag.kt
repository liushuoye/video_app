package com.shuoye.video.database.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TODO
 * @program Video
 * @ClassName Tag
 * @author shuoye
 * @create 2021-10-23 17:18
 **/
@Entity(tableName = "tag")
data class Tag(
    @PrimaryKey
    val id: Int,
    val animeInfoId: Int,
    val name: String,
)
