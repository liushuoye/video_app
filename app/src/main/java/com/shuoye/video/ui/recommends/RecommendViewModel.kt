package com.shuoye.video.ui.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shuoye.video.database.pojo.Recommend
import com.shuoye.video.database.repository.RecommendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName RecommendViewModel
 * @author shuoye
 * @create 2021-11-04 13:41
 **/
@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val repository: RecommendRepository
) : ViewModel() {
    private var updates: Flow<PagingData<Recommend>>? = null;

    fun getRecommend(): Flow<PagingData<Recommend>> {
        updates?.apply { return this }
        val newResult = repository.getRecommend().cachedIn(viewModelScope)
        updates = newResult
        return newResult
    }
}