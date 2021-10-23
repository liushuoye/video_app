package com.shuoye.video.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.shuoye.video.database.pojo.BannerKey

/**
 * TODO
 * @program Video
 * @ClassName BannerKeyDao
 * @author shuoye
 * @create 2021-10-23 19:43
 **/
@Dao
interface BannerKeyDao : BaseDao<BannerKey> {
    @Query("DELETE FROM banner_key")
    fun clear()
}