package com.shuoye.video.database.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.RecommendedDaily
import com.shuoye.video.database.repository.remoteMediator.RecommendedDailyRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName RecentUpdatesRepository
 * @author shuoye
 * @create 2021-10-24 12:13
 **/
class RecommendedDailyRepository @Inject constructor(
    netWorkManager: NetWorkManager,
    private val appDatabase: AppDatabase
) {
    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    private val config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE)
    private val pagingSourceFactory = { appDatabase.recommendedDailyDao().findAll() }
    private val remoteMediator = RecommendedDailyRemoteMediator(netWorkManager, appDatabase)

    fun getRecommendedDaily(): Flow<PagingData<RecommendedDaily>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = config,
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}