package com.shuoye.video.database.repository.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.shuoye.video.api.TimeLineService
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.TimeLine

/**
 * TODO
 * @program Video
 * @ClassName TimeLineRemoteMediator
 * @author shuoye
 * @create 2021-10-23 01:34
 **/
@ExperimentalPagingApi
class TimeLineRemoteMediator(
    private val wd: Int,
    private val service: TimeLineService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, TimeLine>() {

    override suspend fun initialize(): InitializeAction {
        // 分页开始后立即启动远程刷新并且不触发远程前置或
        // 追加直到刷新成功。在我们不介意显示过时的情况下，
        // 缓存离线数据，我们可以返回 SKIP_INITIAL_REFRESH 来防止分页
        // 触发远程刷新。
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TimeLine>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
            }
            LoadType.PREPEND -> {

                // 如果 remoteKeys 为 null，则表示刷新结果尚未在数据库中。
                // 我们可以使用 `endOfPaginationReached = false` 返回 Success 因为 Paging
                // 如果 RemoteKeys 变为非空，将再次调用此方法。
                // 如果 remoteKeys 不是 NULL 但它的 prevKey 是 null，这意味着我们已经达到
                // prepend 的分页结束。
            }
            LoadType.APPEND -> {

                // 如果 remoteKeys 为 null，则表示刷新结果尚未在数据库中。
                // 我们可以使用 `endOfPaginationReached = false` 返回 Success 因为 Paging
                // 如果 RemoteKeys 变为非空，将再次调用此方法。
                // 如果 remoteKeys 不是 NULL 但它的 prevKey 是 null，这意味着我们已经达到
                // 追加分页结束。
            }
        }
        return MediatorResult.Success(true)
    }
}