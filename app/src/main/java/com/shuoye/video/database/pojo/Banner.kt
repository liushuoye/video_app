package com.shuoye.video.database.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TODO
 * @program Video
 * @ClassName Banner
 * @author shuoye
 * @create 2021-10-23 17:10
 **/
@Entity(tableName = "banner")
data class Banner(
    @PrimaryKey
    val id: Int,
    val title: String,
    val picUrl: String,
    val time: Long,
    var updatesTime: Long = System.currentTimeMillis()
)
