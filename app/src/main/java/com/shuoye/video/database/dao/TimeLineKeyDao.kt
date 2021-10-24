package com.shuoye.video.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.shuoye.video.database.pojo.TimeLineKey

/**
 * TODO
 * @program Video
 * @ClassName TimeLineKeyDao
 * @author shuoye
 * @create 2021-10-23 10:57
 **/
@Dao
interface TimeLineKeyDao : BaseDao<TimeLineKey> {

    /**
     * 根据 id 查找
     */
    @Query("SELECT * FROM time_line_key WHERE id=:wd")
    suspend fun findKeyById(wd: Int): TimeLineKey?

    /**
     * 清空表
     */
    @Query("DELETE FROM time_line_key")
    suspend fun clear()
}