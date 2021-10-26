package com.shuoye.video.database.repository

import androidx.lifecycle.LiveData
import com.shuoye.video.AppExecutors
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.api.request.Resource
import com.shuoye.video.api.response.ApiResponse
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.Player
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName PlayerRepository
 * @author shuoye
 * @create 2021-11-02 21:44
 **/
class PlayerRepository @Inject constructor(
    private val netWorkManager: NetWorkManager,
    private val appDatabase: AppDatabase,
    private val appExecutors: AppExecutors,
) {

    fun getPlayers(animeInfoId: Int): LiveData<Resource<List<Player>>> {
        return object : NetworkBoundResource<List<Player>, List<List<Player>>>(appExecutors) {
            override fun saveCallResult(item: List<List<Player>>) {
                for (players in item) {
                    appDatabase.playerDao().insert(players)
                }
            }

            override fun shouldFetch(data: List<Player>?): Boolean {
                return data?.isEmpty() == true
            }

            override fun loadFromDb(): LiveData<List<Player>> {
                return appDatabase.playerDao().findByPlayId(animeInfoId, "<play>web_mp4</play>")
            }

            override fun createCall(): LiveData<ApiResponse<List<List<Player>>>> {
                return netWorkManager.create().getPlayer(animeInfoId)
            }
        }.asLiveData()
    }
}