package com.shuoye.video.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.shuoye.video.database.pojo.Anime

/**
 * TODO
 * @program Video
 * @ClassName AnimeDao
 * @author shuoye
 * @create 2021-10-23 19:02
 **/
@Dao
interface AnimeDao {
    @Transaction
    @Query("SELECT * FROM anime_info WHERE id = :id")
    fun findById(id: Int): LiveData<Anime>

}