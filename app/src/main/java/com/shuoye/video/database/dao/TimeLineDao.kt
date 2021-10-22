package com.shuoye.video.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.shuoye.video.database.pojo.TimeLine

/**
 * TODO
 * @program Video
 * @ClassName TimeLineDao
 * @author shuoye
 * @create 2021-10-23 00:27
 **/
@Dao
interface TimeLineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timeLine: TimeLine)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(timeLines: List<TimeLine>)
}