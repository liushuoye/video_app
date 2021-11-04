package com.shuoye.video.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.shuoye.video.database.pojo.Search

/**
 * TODO
 * @program Video
 * @ClassName UpdateDao
 * @author shuoye
 * @create 2021-11-04 15:29
 **/
@Dao
interface SearchDao : BaseDao<Search> {
    @Query("DELETE FROM anime_search")
    suspend fun clear()

    @Query("SELECT * FROM anime_search")
    fun findAll(): PagingSource<Int, Search>
}