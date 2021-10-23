package com.shuoye.video.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shuoye.video.database.pojo.TimeLine

/**
 * TODO
 * @program Video
 * @ClassName TimeLineDao
 * @author shuoye
 * @create 2021-10-23 00:27
 **/
@Dao
interface TimeLineDao : BaseDao<TimeLine> {

    @Query("SELECT * FROM time_line WHERE wd = :wd")
    fun findByWd(wd: Int): PagingSource<Int, TimeLine>

    /**
     * 批量插入
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserts(vararg: List<TimeLine>)

    @Query("DELETE FROM time_line")
    suspend fun clear()
}