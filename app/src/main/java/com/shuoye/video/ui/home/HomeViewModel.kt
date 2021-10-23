package com.shuoye.video.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shuoye.video.database.pojo.Banner
import com.shuoye.video.database.pojo.TimeLine
import com.shuoye.video.database.repository.BannerRepository
import com.shuoye.video.database.repository.TimeLineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 视图模型
 * @program video
 * @ClassName HomeViewModel
 * @author shuoye
 * @create 2021-10-21 20:00
 **/
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val timeLineRepository: TimeLineRepository,
    private val bannerRepository: BannerRepository
) : ViewModel() {
    private var timeLines: Flow<PagingData<TimeLine>>? = null
    private var banners: Flow<PagingData<Banner>>? = null
    val banner = ArrayList<Banner>()


    fun getTimeLines(wd: Int): Flow<PagingData<TimeLine>> {
        timeLines?.apply { return this }
        val newResult = timeLineRepository.getTimeLines(wd).cachedIn(viewModelScope)
        timeLines = newResult
        return newResult
    }

    fun getBanner(): Flow<PagingData<Banner>> {
        banners?.apply { return this }
        val newResult = bannerRepository.getBanners().cachedIn(viewModelScope)
        banners = newResult
        return newResult
    }
}