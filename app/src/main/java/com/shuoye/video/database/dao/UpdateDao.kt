package com.shuoye.video.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.shuoye.video.database.pojo.Update

/**
 * TODO
 * @program Video
 * @ClassName UpdateDao
 * @author shuoye
 * @create 2021-11-04 15:29
 **/
@Dao
interface UpdateDao : BaseDao<Update> {
    @Query("DELETE FROM anime_update")
    suspend fun clear()

    @Query("SELECT * FROM anime_update ORDER BY uuid ASC")
    fun findAll(): PagingSource<Int, Update>
}