package com.shuoye.video.database.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.TimeLine
import com.shuoye.video.database.repository.remoteMediator.TimeLineRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 新番时间表资源库
 * @program video
 * @ClassName TimeLineRepository
 * @author shuoye
 * @create 2021-10-21 21:10
 **/

class TimeLineRepository @Inject constructor(
    private val service: NetWorkManager,
    private val appDatabase: AppDatabase
) {

    fun getTimeLines(wd: Int): Flow<PagingData<TimeLine>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            remoteMediator = TimeLineRemoteMediator(service, appDatabase),
            pagingSourceFactory = { appDatabase.timeLineDao().findByWd(wd) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}