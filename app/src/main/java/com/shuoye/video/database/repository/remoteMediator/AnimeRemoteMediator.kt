package com.shuoye.video.database.repository.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.Anime
import retrofit2.HttpException
import java.io.IOException

/**
 * TODO
 * @program Video
 * @ClassName AnimeRemoteMediator
 * @author shuoye
 * @create 2021-10-24 01:19
 **/
@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator(
    private val netWorkManager: NetWorkManager,
    private val appDatabase: AppDatabase,
    private val id: Int
) : RemoteMediator<Int, Anime>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Anime>): MediatorResult {
        when (loadType) {
            LoadType.REFRESH -> {

            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.APPEND -> {
                return MediatorResult.Success(endOfPaginationReached = false)
            }
        }
        try {
            val response = netWorkManager.create().getAnimeInfo(id)
            val animeInfo = response.data
            val endOfPaginationReached = animeInfo.isEmpty()
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.animeInfoDao().clear()
                    appDatabase.tagDao().clear()
                }
                animeInfo.forEach {
                    appDatabase.animeInfoDao().insert(it.series)
                    appDatabase.tagDao().insert(it.tags)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (ex: IOException) {
            return MediatorResult.Error(ex)
        } catch (ex: HttpException) {
            return MediatorResult.Error(ex)
        }
    }
}