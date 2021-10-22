package com.shuoye.video.database.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shuoye.video.api.TimeLineService
import com.shuoye.video.database.repository.pagingSource.TimeLinePagingSource
import com.shuoye.video.database.pojo.TimeLine
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
    private val service: TimeLineService
) {
    fun getTimeLine(): Flow<PagingData<TimeLine>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { TimeLinePagingSource(service) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}