package com.shuoye.video.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**
 * TODO
 * @program Video
 * @ClassName BaseDao
 * @author shuoye
 * @create 2021-10-23 10:58
 **/
@Dao
interface BaseDao<T> {
    /**
     * 插入更新
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg: T)

    /**
     * 批量插入
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg: List<T>)

}