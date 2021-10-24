package com.shuoye.video.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.shuoye.video.database.pojo.Tag

/**
 * TODO
 * @program Video
 * @ClassName TagDao
 * @author shuoye
 * @create 2021-10-23 18:26
 **/
@Dao
interface TagDao : BaseDao<Tag> {
    @Query("DELETE FROM tag")
    suspend fun clear()
}