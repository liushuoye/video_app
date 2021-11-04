package com.shuoye.video.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shuoye.video.api.request.Resource
import com.shuoye.video.database.pojo.Banner
import com.shuoye.video.database.repository.BannerRepository
import com.shuoye.video.database.repository.TimeLineRepository
import com.shuoye.video.ui.home.adapters.ImageBannerAdapter
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
    private var banners: Flow<PagingData<Banner>>? = null
    val banner = ArrayList<Banner>()
    var adapter: ImageBannerAdapter? = null


    fun getBanner(): Flow<PagingData<Banner>> {
        banners?.apply { return this }
        val newResult = bannerRepository.getBanners().cachedIn(viewModelScope)
        banners = newResult
        return newResult
    }

    fun getBannerLiveData(): LiveData<Resource<List<Banner>>> {
        return bannerRepository.getBannerLiveData()
    }
}