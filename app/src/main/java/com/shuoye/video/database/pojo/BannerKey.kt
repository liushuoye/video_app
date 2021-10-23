package com.shuoye.video.database.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TODO
 * @program Video
 * @ClassName BannerKey
 * @author shuoye
 * @create 2021-10-23 21:16
 **/
@Entity(tableName = "banner_key")
data class BannerKey(
    @PrimaryKey
    val id: Int,
    val time: Long = System.currentTimeMillis()
)

