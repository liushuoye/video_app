package com.shuoye.video.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.shuoye.video.database.pojo.Banner

/**
 * TODO
 * @program Video
 * @ClassName BannerDao
 * @author shuoye
 * @create 2021-10-23 18:28
 **/
@Dao
interface BannerDao : BaseDao<Banner> {
    @Query("DELETE FROM banner")
    suspend fun clear()

    @Query("SELECT * FROM banner")
    fun findAll(): PagingSource<Int, Banner>
}