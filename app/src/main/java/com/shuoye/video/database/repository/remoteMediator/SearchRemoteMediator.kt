package com.shuoye.video.database.repository.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.Search
import retrofit2.HttpException
import java.io.IOException

/**
 * TODO
 * @program Video
 * @ClassName SearchRemoteMediator
 * @author shuoye
 * @create 2021-11-04 14:55
 **/
@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val netWorkManager: NetWorkManager,
    private val appDatabase: AppDatabase,
    private val query: String,
) : RemoteMediator<Int, Search>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Search>
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
            val response = netWorkManager.create().getSearch(query, 1)
            val search = response.data
            val endOfPaginationReached: Boolean = search.isEmpty()
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.searchDao().clear()
                }
                appDatabase.searchDao().insert(search)

            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (ex: IOException) {
            return MediatorResult.Error(ex)
        } catch (ex: HttpException) {
            return MediatorResult.Error(ex)
        }
    }
}