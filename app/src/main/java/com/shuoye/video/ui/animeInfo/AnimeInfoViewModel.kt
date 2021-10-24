package com.shuoye.video.ui.animeInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shuoye.video.api.request.Resource
import com.shuoye.video.api.response.ApiResponse
import com.shuoye.video.database.pojo.Anime
import com.shuoye.video.database.pojo.AnimeInfo
import com.shuoye.video.database.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName AnimeInfoViewModel
 * @author shuoye
 * @create 2021-10-24 22:03
 **/
@HiltViewModel
class AnimeInfoViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {
    private var anime: LiveData<Resource<Anime>>? = null

    fun getTest(id: Int): LiveData<ApiResponse<AnimeInfo>> {
        return animeRepository.getTest(id)
    }

    fun getAnime(id: Int): LiveData<Resource<Anime>> {
        anime?.apply {
            return this
        }
        val newAnime = animeRepository.getAnime(id)
        anime = newAnime
        return newAnime
    }
}