package com.shuoye.video.database.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.Search
import com.shuoye.video.database.repository.remoteMediator.SearchRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName SearchRepository
 * @author shuoye
 * @create 2021-11-04 20:08
 **/
class SearchRepository @Inject constructor(
    private val netWorkManager: NetWorkManager,
    private val appDatabase: AppDatabase
) {
    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    private val config = PagingConfig(
        enablePlaceholders = false,
        pageSize = NETWORK_PAGE_SIZE,
        prefetchDistance = 1,
    )

    fun getSearch(query: String): Flow<PagingData<Search>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = config,
            remoteMediator = SearchRemoteMediator(netWorkManager, appDatabase, query),
            pagingSourceFactory = { appDatabase.searchDao().findAll() }
        ).flow
    }
}