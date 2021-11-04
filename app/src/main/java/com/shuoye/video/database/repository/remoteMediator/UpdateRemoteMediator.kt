package com.shuoye.video.database.repository.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.Update
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
class UpdateRemoteMediator(
    private val netWorkManager: NetWorkManager,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Update>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Update>
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
            val response = netWorkManager.create().getUpdate()
            val updates = response.data
            val endOfPaginationReached: Boolean = updates.isEmpty()
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.updateDao().clear()
                }
                appDatabase.updateDao().insert(updates)

            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (ex: IOException) {
            return MediatorResult.Error(ex)
        } catch (ex: HttpException) {
            return MediatorResult.Error(ex)
        }
    }
}