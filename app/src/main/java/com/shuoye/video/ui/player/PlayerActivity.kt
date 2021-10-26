package com.shuoye.video.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cn.jzvd.Jzvd
import com.shuoye.video.R
import com.shuoye.video.databinding.ActivityPlayBinding
import com.shuoye.video.ui.player.view.JZExoPlayer
import com.shuoye.video.ui.player.view.JZPlayer

/**
 * TODO
 * @program Video
 * @ClassName PlayerActivity
 * @author shuoye
 * @create 2021-10-26 02:59
 **/
class PlayerActivity : AppCompatActivity(), JZPlayer.CompleteListener, JZPlayer.TouchListener,
    JZPlayer.ShowOrHideChangeViewListener {
    //点击索引
    private var clickIndex = 0

    // 有预视频
    private val hasPreVideo = false

    // 有下一个视频
    private val hasNextVideo = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityPlayBinding>(this, R.layout.activity_play)
        initPlayer(binding)

    }

    private fun initPlayer(binding: ActivityPlayBinding) {
        val url =
            "https://1251316161.vod2.myqcloud.com/007a649dvodcq1251316161/845bd2375285890805870943674/8OdbyaAAVYEA.mp4"
        val witchTitle = "青春期笨蛋不做兔女郎学姐的梦"
        binding.player.setListener(this, this, this, this)

        binding.player.setUp(url, witchTitle, Jzvd.SCREEN_FULLSCREEN, JZExoPlayer::class.java)
    }

    override fun complete() {

    }

    override fun touch() {

    }

    override fun showOrHideChangeView() {

    }
}