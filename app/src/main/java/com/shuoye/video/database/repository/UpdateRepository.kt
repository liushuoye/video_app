package com.shuoye.video.database.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.AnimeSimple
import com.shuoye.video.database.pojo.Update
import com.shuoye.video.database.repository.remoteMediator.UpdateRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName UpdateRepository
 * @author shuoye
 * @create 2021-11-04 14:49
 **/
class UpdateRepository @Inject constructor(
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

    fun getUpdate(): Flow<PagingData<Update>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = config,
            remoteMediator = UpdateRemoteMediator(netWorkManager, appDatabase),
            pagingSourceFactory = { appDatabase.updateDao().findAll() }
        ).flow
    }
}