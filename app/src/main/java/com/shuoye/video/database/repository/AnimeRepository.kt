package com.shuoye.video.database.repository

import androidx.lifecycle.LiveData
import com.shuoye.video.AppExecutors
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.api.request.Resource
import com.shuoye.video.api.response.ApiResponse
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.Anime
import com.shuoye.video.database.pojo.AnimeInfo
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName AnimeRepository
 * @author shuoye
 * @create 2021-10-24 22:09
 **/
class AnimeRepository @Inject constructor(
    private val netWorkManager: NetWorkManager,
    private val appDatabase: AppDatabase,
    private val appExecutors: AppExecutors,
) {
    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    fun getTest(id: Int): LiveData<ApiResponse<AnimeInfo>> {
        return netWorkManager.create().getAnimeInfo(id)
    }

    fun getAnime(id: Int): LiveData<Resource<Anime>> {
        return object : NetworkBoundResource<Anime, AnimeInfo>(appExecutors) {
            override fun saveCallResult(item: AnimeInfo) {
                appDatabase.animeInfoDao().insert(item.series)
                appDatabase.tagDao().insert(item.tags)
            }

            override fun shouldFetch(data: Anime?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Anime> {
                return appDatabase.animeDao().findById(id)
            }

            override fun createCall(): LiveData<ApiResponse<AnimeInfo>> {

                val animeInfo = netWorkManager.create().getAnimeInfo(id)
                return animeInfo
            }

        }.asLiveData()
    }


}