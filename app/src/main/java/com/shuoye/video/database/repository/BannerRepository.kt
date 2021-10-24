package com.shuoye.video.database.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shuoye.video.AppExecutors
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.api.request.Resource
import com.shuoye.video.api.response.ApiResponse
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.Banner
import com.shuoye.video.database.repository.remoteMediator.BannerRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName BannerRepository
 * @author shuoye
 * @create 2021-10-23 19:57
 **/
class BannerRepository @Inject constructor(
    private val netWorkManager: NetWorkManager,
    private val appDatabase: AppDatabase,
    private val appExecutors: AppExecutors,
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
            remoteMediator = BannerRemoteMediator(netWorkManager, appDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getBannerLiveData(): LiveData<Resource<List<Banner>>> {
        return object : NetworkBoundResource<List<Banner>, List<Banner>>(appExecutors) {
            override fun saveCallResult(item: List<Banner>) {
                appDatabase.bannerDao().clear()
                item.forEach() {
                    it.updatesTime = System.currentTimeMillis()
                }
                appDatabase.bannerDao().insert(item)
            }

            override fun shouldFetch(data: List<Banner>?): Boolean {
                runBlocking{
                    
                }
//                val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
//                val time = appDatabase.bannerKeyDao().findKeyById(1)?.time
//                time ?: return true
//                return System.currentTimeMillis() - time > cacheTimeout
                data?.lastOrNull {
                    val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
                    return System.currentTimeMillis() - it.updatesTime > cacheTimeout
                }
                return true
            }

            override fun loadFromDb(): LiveData<List<Banner>> {
                return appDatabase.bannerDao().findAllLiveData()
            }

            override fun createCall(): LiveData<ApiResponse<List<Banner>>> {

                return netWorkManager.create().getBannerTest()
            }

        }.asLiveData()
    }

}