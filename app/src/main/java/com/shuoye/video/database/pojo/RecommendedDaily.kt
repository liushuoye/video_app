package com.shuoye.video.database.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TODO
 * @program Video
 * @ClassName RecommendedDaily
 * @author shuoye
 * @create 2021-10-24 11:27
 **/
@Entity(tableName = "recommended_daily")
data class RecommendedDaily(
    @PrimaryKey
    val id: Int,
    val name: String,
    val newTitle: String,
    val cover: String
)
