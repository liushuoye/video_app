package com.shuoye.video.database.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.RecentUpdates
import com.shuoye.video.database.repository.remoteMediator.RecentUpdatesRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName RecentUpdatesRepository
 * @author shuoye
 * @create 2021-10-24 12:13
 **/
class RecentUpdatesRepository @Inject constructor(
    netWorkManager: NetWorkManager,
    private val appDatabase: AppDatabase
) {
    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    private val config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE)
    private val pagingSourceFactory = { appDatabase.recentUpdatesDao().findAll() }
    private val remoteMediator = RecentUpdatesRemoteMediator(netWorkManager, appDatabase)

    fun getRecentUpdates(): Flow<PagingData<RecentUpdates>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = config,
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}