package com.shuoye.video.database.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.shuoye.video.AppExecutors
import com.shuoye.video.TAG
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


    fun getTest(id: Int): LiveData<ApiResponse<AnimeInfo>> {
        return netWorkManager.create().getAnimeInfo(id)
    }

    fun getAnime(id: Int): LiveData<Resource<Anime>> {
        return object : NetworkBoundResource<Anime, AnimeInfo>(appExecutors) {
            override fun saveCallResult(item: AnimeInfo) {
                Log.d(TAG, "saveCallResult: $item")
                appDatabase.animeInfoDao().insert(item.series)
                item.series.forEach() {
                    Log.d(TAG, "saveCallResult: tag:${it.tags}")
                    if (it.id == 0) {
                        appDatabase.tagDao().insert(it.tags)
                    }

                }

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