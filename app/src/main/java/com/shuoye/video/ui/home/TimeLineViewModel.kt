package com.shuoye.video.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shuoye.video.database.pojo.TimeLine
import com.shuoye.video.database.repository.TimeLineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName TimeLineViewModel
 * @author shuoye
 * @create 2021-10-24 15:24
 **/
@HiltViewModel
class TimeLineViewModel @Inject constructor(
    private val timeLineRepository: TimeLineRepository
) : ViewModel() {
    private var timeLines: Flow<PagingData<TimeLine>>? = null

    fun getTimeLines(wd: Int): Flow<PagingData<TimeLine>> {
        timeLines?.apply { return this }
        val newResult = timeLineRepository.getTimeLines(wd).cachedIn(viewModelScope)
        timeLines = newResult
        return newResult
    }
}