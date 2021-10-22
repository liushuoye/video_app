package com.shuoye.video.database.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 新番时间表实体类
 * @program Video
 * @ClassName TimeLine
 * @author shuoye
 * @create 2021-10-21 20:48
 **/
@Entity(tableName = "time_line")
data class TimeLine(
    @PrimaryKey
    val id: Int,
    val isNew: Boolean,
    val time: String,
    val name: String,
    val nameForNew: String,
    val wd: Int

)
