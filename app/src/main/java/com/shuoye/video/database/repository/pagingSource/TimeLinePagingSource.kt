package com.shuoye.video.database.repository.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shuoye.video.api.TimeLineService
import com.shuoye.video.database.pojo.TimeLine

private const val UNSPLASH_STARTING_PAGE_INDEX = 0

/**
 * 分页从服务器获取资源
 * @program Video
 * @ClassName TimeLinePagingSource
 * @author shuoye
 * @create 2021-10-21 21:22
 **/
class TimeLinePagingSource(
    private val service: TimeLineService,
) : PagingSource<Int, TimeLine>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TimeLine> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        val loadResult = try {
            val response = service.getTimeLine(page)
            val photos = response.data
            LoadResult.Page(
                data = photos,
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == 6) null else page + 1
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
        return loadResult

    }

    override fun getRefreshKey(state: PagingState<Int, TimeLine>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // 这从上一页开始加载，但由于 PagingConfig.initialLoadSize 跨越
            // 多个页面，初始加载仍将加载以周围为中心的项目
            // 锚定位置。这也可以防止由于以下原因需要立即启动 prepend
            // 预取距离。
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}