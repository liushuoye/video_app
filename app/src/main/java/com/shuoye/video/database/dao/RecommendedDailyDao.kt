package com.shuoye.video.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.shuoye.video.database.pojo.RecommendedDaily

/**
 * TODO
 * @program Video
 * @ClassName RecommendedDailyDao
 * @author shuoye
 * @create 2021-10-24 11:29
 **/
@Dao
interface RecommendedDailyDao : BaseDao<RecommendedDaily> {
    @Query("DELETE FROM recommended_daily")
    suspend fun clear()

    @Query("SELECT * FROM recommended_daily")
    fun findAll(): PagingSource<Int, RecommendedDaily>
}