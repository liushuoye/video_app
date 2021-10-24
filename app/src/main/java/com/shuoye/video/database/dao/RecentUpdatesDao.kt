package com.shuoye.video.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.shuoye.video.database.pojo.RecentUpdates

/**
 * TODO
 * @program Video
 * @ClassName RecentUpdatesDao
 * @author shuoye
 * @create 2021-10-24 11:20
 **/
@Dao
interface RecentUpdatesDao : BaseDao<RecentUpdates> {
    @Query("DELETE FROM recent_updates")
    suspend fun clear()

    @Query("SELECT * FROM recent_updates")
    fun findAll(): PagingSource<Int, RecentUpdates>
}