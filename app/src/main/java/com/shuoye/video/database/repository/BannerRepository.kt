package com.shuoye.video.database.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.Banner
import com.shuoye.video.database.repository.remoteMediator.BannerRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName BannerRepository
 * @author shuoye
 * @create 2021-10-23 19:57
 **/
class BannerRepository @Inject constructor(
    private val service: NetWorkManager,
    private val appDatabase: AppDatabase
) {
    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    private val config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE)
    private val pagingSourceFactory = { appDatabase.bannerDao().findAll() }

    fun getBanners(): Flow<PagingData<Banner>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = config,
            remoteMediator = BannerRemoteMediator(service, appDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}