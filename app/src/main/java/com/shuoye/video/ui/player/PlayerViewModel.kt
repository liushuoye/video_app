package com.shuoye.video.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shuoye.video.api.request.Resource
import com.shuoye.video.database.pojo.Player
import com.shuoye.video.database.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

/**
 * TODO
 * @program Video
 * @ClassName PlayerViewModel
 * @author shuoye
 * @create 2021-11-02 18:29
 **/
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {
    var animeInfoId: Int = 20180213
    var url: String? = null
    var siteUrl: String? = null

    //番剧名称
    var animeTitle: String = ""

    //集数名称
    var witchTitle: String? = null

    //播放索引
    var clickIndex = 0


    // 播放列表
    val players: ArrayList<Player> = ArrayList<Player>()
    private var playersLiveData: LiveData<Resource<List<Player>>>? = null

    fun getPlayers(animeInfoId: Int): LiveData<Resource<List<Player>>> {
        if (this.animeInfoId == animeInfoId && playersLiveData != null) {
            return playersLiveData as LiveData<Resource<List<Player>>>
        }
        this.animeInfoId = animeInfoId
        playersLiveData = playerRepository.getPlayers(animeInfoId)
        return playersLiveData as LiveData<Resource<List<Player>>>
    }
}