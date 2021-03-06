package com.shuoye.video.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.shuoye.video.database.pojo.AnimeInfo

/**
 * TODO
 * @program Video
 * @ClassName AnimeInfoDao
 * @author shuoye
 * @create 2021-10-23 18:29
 **/
@Dao
interface AnimeInfoDao : BaseDao<AnimeInfo> {
    @Query("DELETE FROM anime_info")
    suspend fun clear()
}