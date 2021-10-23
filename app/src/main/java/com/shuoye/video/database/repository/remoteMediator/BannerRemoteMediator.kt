package com.shuoye.video.database.repository.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.Banner
import com.shuoye.video.database.pojo.BannerKey
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

/**
 * TODO
 * @program Video
 * @ClassName BannerRemoteMediator
 * @author shuoye
 * @create 2021-10-23 01:34
 **/
@OptIn(ExperimentalPagingApi::class)
class BannerRemoteMediator(
    private val service: NetWorkManager,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Banner>() {

    override suspend fun initialize(): InitializeAction {
        // 分页开始后立即启动远程刷新并且不触发远程前置或
        // 追加直到刷新成功。在我们不介意显示过时的情况下，
        // 缓存离线数据，我们可以返回 SKIP_INITIAL_REFRESH 来防止分页
        // 触发远程刷新。LAUNCH_INITIAL_REFRESH
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Banner>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                GITHUB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.APPEND -> {
                return MediatorResult.Success(endOfPaginationReached = false)
            }
        }
        try {
            val response = service.create().getBanner()
            val banners = response.data
            val endOfPaginationReached: Boolean = banners.isEmpty()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // 清除数据库中的所有表
                    appDatabase.bannerDao().clear()
                    appDatabase.bannerKeyDao().clear()
                }
                val keys = BannerKey(page)
                appDatabase.bannerDao().insert(banners)
                appDatabase.bannerKeyDao().insert(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (ex: IOException) {
            return MediatorResult.Error(ex)
        } catch (ex: HttpException) {
            return MediatorResult.Error(ex)
        }
    }
}