package com.shuoye.video.database.pojo

import androidx.room.Embedded
import androidx.room.Relation

/**
 * TODO
 * @program Video
 * @ClassName Anime
 * @author shuoye
 * @create 2021-10-23 18:55
 **/
data class Anime(
    @Embedded
    val animeInfo: AnimeInfo,
    @Relation(
        parentColumn = "id",
        entityColumn = "animeInfoId"
    )
    val tags: List<Tag>,
    @Relation(
        parentColumn = "seriesName",
        entityColumn = "seriesName"
    )
    val series: List<AnimeInfo>
)
