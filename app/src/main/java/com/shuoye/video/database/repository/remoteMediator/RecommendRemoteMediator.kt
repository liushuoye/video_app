package com.shuoye.video.database.repository.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.Recommend
import retrofit2.HttpException
import java.io.IOException

/**
 * TODO
 * @program Video
 * @ClassName UpdateRemoteMediator
 * @author shuoye
 * @create 2021-11-04 14:55
 **/
@OptIn(ExperimentalPagingApi::class)
class RecommendRemoteMediator(
    private val netWorkManager: NetWorkManager,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Recommend>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Recommend>
    ): MediatorResult {
        when (loadType) {
            LoadType.REFRESH -> {
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
        try {
            val response = netWorkManager.create().getRecommend()
            val recommends = response.data
            val endOfPaginationReached: Boolean = recommends.isEmpty()
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.recommendDao().clear()
                }
                appDatabase.recommendDao().insert(recommends)

            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (ex: IOException) {
            return MediatorResult.Error(ex)
        } catch (ex: HttpException) {
            return MediatorResult.Error(ex)
        }
    }
}