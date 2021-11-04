package com.shuoye.video.ui.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shuoye.video.database.pojo.Update
import com.shuoye.video.database.repository.UpdateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName UpdateViewModel
 * @author shuoye
 * @create 2021-11-04 13:41
 **/
@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val repository: UpdateRepository
) : ViewModel() {
    private var updates: Flow<PagingData<Update>>? = null;

    fun getUpdate(): Flow<PagingData<Update>> {
        updates?.apply { return this }
        val newResult = repository.getUpdate().cachedIn(viewModelScope)
        updates = newResult
        return newResult
    }
}