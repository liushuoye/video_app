package com.shuoye.video.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.shuoye.video.database.pojo.Recommend

/**
 * TODO
 * @program Video
 * @ClassName UpdateDao
 * @author shuoye
 * @create 2021-11-04 15:29
 **/
@Dao
interface RecommendDao : BaseDao<Recommend> {
    @Query("DELETE FROM anime_recommend")
    suspend fun clear()

    @Query("SELECT * FROM anime_recommend ORDER BY uuid ASC")
    fun findAll(): PagingSource<Int, Recommend>
}