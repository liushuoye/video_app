package com.shuoye.video.database.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TODO
 * @program Video
 * @ClassName RecentUpdates
 * @author shuoye
 * @create 2021-10-24 11:16
 **/
@Entity(tableName = "recent_updates")
data class RecentUpdates(
    @PrimaryKey
    val id: Int,
    val name: String,
    val newTitle: String,
    val cover: String
)
