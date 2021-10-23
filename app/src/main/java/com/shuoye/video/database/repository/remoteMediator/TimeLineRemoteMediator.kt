package com.shuoye.video.database.repository.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shuoye.video.api.NetWorkManager
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.pojo.TimeLine
import com.shuoye.video.database.pojo.TimeLineKey
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1
/**
 * TODO
 * @program Video
 * @ClassName TimeLineRemoteMediator
 * @author shuoye
 * @create 2021-10-23 01:34
 **/
@OptIn(ExperimentalPagingApi::class)
class TimeLineRemoteMediator(
    private val service: NetWorkManager,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, TimeLine>() {

    override suspend fun initialize(): InitializeAction {
        // 分页开始后立即启动远程刷新并且不触发远程前置或
        // 追加直到刷新成功。在我们不介意显示过时的情况下，
        // 缓存离线数据，我们可以返回 SKIP_INITIAL_REFRESH 来防止分页
        // 触发远程刷新。LAUNCH_INITIAL_REFRESH
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TimeLine>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val timeLineKey = getTimeLineKeyClosestToCurrentPosition(state)
                timeLineKey?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val timeLineKey = getTimeLineKeyForFirstItem(state)
                // 如果 remoteKeys 为 null，则表示刷新结果尚未在数据库中。
                // 我们可以使用 `endOfPaginationReached = false` 返回 Success 因为 Paging
                // 如果 RemoteKeys 变为非空，将再次调用此方法。
                // 如果 remoteKeys 不是 NULL 但它的 prevKey 是 null，这意味着我们已经达到
                // prepend 的分页结束。
                val prevKey = timeLineKey?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = timeLineKey != null)
                prevKey
            }
            LoadType.APPEND -> {
                val timeLineKey = getTimeLineKeyForLastItem(state)
                // 如果 remoteKeys 为 null，则表示刷新结果尚未在数据库中。
                // 我们可以使用 `endOfPaginationReached = false` 返回 Success 因为 Paging
                // 如果 RemoteKeys 变为非空，将再次调用此方法。
                // 如果 remoteKeys 不是 NULL 但它的 prevKey 是 null，这意味着我们已经达到
                // 追加分页结束。
                val nextKey = timeLineKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = timeLineKey != null)
                nextKey
            }
        }
        try {
            val response = service.create().getTimeLine(page)
            val timeLines = response.data
            val endOfPaginationReached: Boolean = timeLines.isEmpty()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // 清除数据库中的所有表
                    appDatabase.timeLineDao().clear()
                    appDatabase.timeLineKeyDao().clear()
                }
                val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = timeLines.map {
                    TimeLineKey(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.timeLineDao().insert(timeLines)
                appDatabase.timeLineKeyDao().insert(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (ex: IOException) {
            return MediatorResult.Error(ex)
        } catch (ex: HttpException) {
            return MediatorResult.Error(ex)
        }
    }

    private suspend fun getTimeLineKeyForLastItem(state: PagingState<Int, TimeLine>): TimeLineKey? {
        // 获取检索到的最后一页，其中包含项目。
        // 从最后一页，获取最后一项
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { timeLine ->
                // 获取检索到的最后一项的远程密钥
                appDatabase.timeLineKeyDao().findKeyById(timeLine.id)
            }
    }

    private suspend fun getTimeLineKeyForFirstItem(state: PagingState<Int, TimeLine>): TimeLineKey? {
        // 获取检索到的第一页，其中包含项目。
        // 从第一页，获取第一项
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { timeLine ->
                // 获取检索到的第一个项目的远程密钥
                appDatabase.timeLineKeyDao().findKeyById(timeLine.id)
            }
    }


    private suspend fun getTimeLineKeyClosestToCurrentPosition(
        state: PagingState<Int, TimeLine>
    ): TimeLineKey? {
        // 分页库正在尝试在锚点位置之后加载数据
        // 获取最接近锚点位置的项目
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                appDatabase.timeLineKeyDao().findKeyById(id)
            }
        }
    }
}