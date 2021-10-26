package com.shuoye.video.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.shuoye.video.database.pojo.Player

/**
 * TODO
 * @program Video
 * @ClassName PlayerDao
 * @author shuoye
 * @create 2021-11-02 21:59
 **/
@Dao
interface PlayerDao : BaseDao<Player> {
    @Query("DELETE FROM player")
    suspend fun clear()

    @Query("SELECT * FROM player WHERE id=:id AND animeInfoId=:animeInfoId ORDER BY ex ASC")
    fun findById(animeInfoId: Int, id: Int): LiveData<List<Player>>

    @Query("SELECT * FROM player WHERE playId=:playId AND animeInfoId=:animeInfoId ORDER BY ex ASC")
    fun findByPlayId(animeInfoId: Int, playId: String): LiveData<List<Player>>
}