package com.shuoye.video.database.repository.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.RecentUpdates
import retrofit2.HttpException
import java.io.IOException

/**
 * TODO
 * @program Video
 * @ClassName RecentUpdatesRemoteMediator
 * @author shuoye
 * @create 2021-10-24 11:07
 **/
@OptIn(ExperimentalPagingApi::class)
class RecentUpdatesRemoteMediator(
    private val netWorkManager: NetWorkManager,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, RecentUpdates>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecentUpdates>
    ): MediatorResult {
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
            val response = netWorkManager.create().getRecentUpdates()
            val recentUpdates = response.data
            val endOfPaginationReached = recentUpdates.isEmpty()
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.recentUpdatesDao().clear()
                }
                appDatabase.recentUpdatesDao().insert(recentUpdates)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (ex: IOException) {
            return MediatorResult.Error(ex)
        } catch (ex: HttpException) {
            return MediatorResult.Error(ex)
        }
    }
}