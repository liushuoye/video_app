package com.shuoye.video.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shuoye.video.database.pojo.Search
import com.shuoye.video.database.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName SearchViewModel
 * @author shuoye
 * @create 2021-11-04 20:16
 **/
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {
    private var query: String = ""
    private var search: Flow<PagingData<Search>>? = null

    fun getSearch(query: String): Flow<PagingData<Search>> {
        if (this.query == query&& search != null) {
            return search as Flow<PagingData<Search>>
        }
        val newResult = repository.getSearch(query).cachedIn(viewModelScope)
        search = newResult
        return newResult
    }
}