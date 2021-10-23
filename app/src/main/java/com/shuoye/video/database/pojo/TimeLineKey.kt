package com.shuoye.video.database.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TODO
 * @program Video
 * @ClassName TimeLineKey
 * @author shuoye
 * @create 2021-10-23 10:46
 **/
@Entity(tableName = "time_line_key")
data class TimeLineKey(
    @PrimaryKey
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?

)
