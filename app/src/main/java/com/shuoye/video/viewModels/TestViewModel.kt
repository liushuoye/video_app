package com.shuoye.video.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shuoye.video.database.pojo.TimeLine
import com.shuoye.video.database.repository.TimeLineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 视图模型
 * @program video
 * @ClassName TestViewModel
 * @author shuoye
 * @create 2021-10-21 20:00
 **/
@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: TimeLineRepository
) : ViewModel() {
    private var timeLines: Flow<PagingData<TimeLine>>? = null


    fun getTimeLines(wd: Int): Flow<PagingData<TimeLine>> {
        val newResult = repository.getTimeLines(wd).cachedIn(viewModelScope)
        timeLines = newResult
        return newResult
    }
}