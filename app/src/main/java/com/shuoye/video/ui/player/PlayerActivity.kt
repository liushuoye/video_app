package com.shuoye.video.ui.player

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.recyclerview.widget.GridLayoutManager
import cn.jzvd.Jzvd
import com.google.android.material.button.MaterialButton
import com.shuoye.video.R
import com.shuoye.video.VideoApplication
import com.shuoye.video.databinding.ActivityPlayBinding
import com.shuoye.video.ui.player.adapter.AnimeDescDramaAdapter
import com.shuoye.video.ui.player.bean.AnimeDescDetailsBean
import com.shuoye.video.ui.player.bean.Event
import com.shuoye.video.ui.player.view.JZExoPlayer
import com.shuoye.video.ui.player.view.JZPlayer
import com.shuoye.video.utils.Utils
import com.shuoye.video.utils.VideoUtils
import org.greenrobot.eventbus.EventBus
import java.util.*


/**
 * TODO
 * @program Video
 * @ClassName PlayerActivity
 * @author shuoye
 * @create 2021-10-26 02:59
 **/
open class PlayerActivity : Activity(), JZPlayer.CompleteListener, JZPlayer.TouchListener,
    JZPlayer.ShowOrHideChangeViewListener {
    companion object {
        private const val PREVIDEOSTR = "上一集：%s"
        private const val NEXTVIDEOSTR = "下一集：%s"
    }

    private val videoApplication = VideoApplication.getInstance()
    private lateinit var binding: ActivityPlayBinding;


    private val animeTitle: String? = null
    private var witchTitle: String? = null
    private var url: String? = null
    private var siteUrl: String? = null

    //点击索引
    private var clickIndex = 0

    // 有预视频
    private var hasPreVideo = false

    // 有下一个视频
    private var hasNextVideo = false

    // 播放列表
    private var list: ArrayList<AnimeDescDetailsBean> = ArrayList<AnimeDescDetailsBean>()

    //展开播放列表适配器
    private var dramaAdapter: AnimeDescDramaAdapter? = null

    //警报对话框
    private var alertDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play)

        //设置状态栏全透明
//        StatusBarUtil.setTransparent(this)
        hideNavBar()
        hideGap()
        initPlayer()
        initAdapter()

    }

    /**
     * 初始化播放器
     */
    private fun initPlayer() {
        val url =
            "https://1251316161.vod2.myqcloud.com/007a649dvodcq1251316161/845bd2375285890805870943674/8OdbyaAAVYEA.mp4"
        //番剧名称
        val animeTitle = "青春期笨蛋不做兔女郎学姐的梦"
        //集数名称
        val witchTitle = "第一话"
        //当前播放剧集下标
        clickIndex = 1
        //剧集list
        list = ArrayList<AnimeDescDetailsBean>()
        list.add(
            AnimeDescDetailsBean(
                "第一话",
                "https://1251316161.vod2.myqcloud.com/007a649dvodcq1251316161/845bd2375285890805870943674/8OdbyaAAVYEA.mp4",
                true
            )
        )
        list.add(
            AnimeDescDetailsBean(
                "第二话",
                "https://1251316161.vod2.myqcloud.com/007a649dvodcq1251316161/845c59965285890805870944519/nSJ8NQQFs7cA.mp4",
                false
            )
        )
        //禁止冒泡
        binding.navView.setOnClickListener { }
        binding.navConfigView.setOnClickListener { }

        setPlayerPreNextTag()

        binding.navView.background?.mutate()?.alpha = 150
        binding.navConfigView.background.mutate().alpha = 150
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        binding.drawerLayout.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    Jzvd.goOnPlayOnPause()
                }
            }

            override fun onDrawerClosed(drawerView: View) {
                if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    Jzvd.goOnPlayOnResume()
                }
            }

            override fun onDrawerStateChanged(newState: Int) {}
        })
        binding.player.config?.setOnClickListener {
            if (!Utils.isFastClick()) {
                return@setOnClickListener
            }
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        binding.player.setListener(this, this, this, this)
        binding.player.backButton.setOnClickListener { finish() }
        // 播放上一个视频
        binding.player.preVideo?.setOnClickListener {
            clickIndex--
            changePlayUrl(clickIndex)
        }
        //播放下一个视频
        binding.player.nextVideo?.setOnClickListener {
            clickIndex++
            changePlayUrl(clickIndex)
        }
        //TODO 加载视频失败，嗅探视频
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            binding.picConfig.visibility = View.GONE
        } else {
            binding.picConfig.visibility = View.VISIBLE
        }
        if (gtSdk23()) {
            binding.player.tvSpeed?.visibility = View.VISIBLE
        } else {
            binding.player.tvSpeed?.visibility = View.GONE
        }
        // 添加链接 信息
        binding.player.setUp(
            url,
            "$animeTitle - $witchTitle",
            Jzvd.SCREEN_FULLSCREEN,
            JZExoPlayer::class.java
        )

        // 全屏
        binding.player.fullscreenButton.setOnClickListener {
            if (!Utils.isFastClick()) {
                return@setOnClickListener
            }
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.END)
            }
        }
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

        // 自动播放
        binding.player.playingShow()
        binding.player.startButton.performClick()
        binding.player.startVideo()
    }

    /**
     * 初始化适配器
     */
    open fun initAdapter() {
        binding.rvList.isNestedScrollingEnabled = false
        binding.rvList.layoutManager = GridLayoutManager(this, 4)
        dramaAdapter = AnimeDescDramaAdapter(this, list)
        binding.rvList.adapter = dramaAdapter
        dramaAdapter!!.setOnItemClickListener { _, _, position ->
            if (!Utils.isFastClick()) {
                return@setOnItemClickListener
            }
            binding.drawerLayout.closeDrawer(GravityCompat.END)
            changePlayUrl(position)
        }
    }

    /**
     * 设置播放器前下一个标签
     */
    private fun setPlayerPreNextTag() {
        hasPreVideo = clickIndex != 0
        binding.player.preVideo?.text = if (hasPreVideo) String.format(
            PREVIDEOSTR,
            list[clickIndex - 1].title
        ) else ""
        hasNextVideo = clickIndex != list.size - 1
        binding.player.nextVideo?.text = if (hasNextVideo) String.format(
            NEXTVIDEOSTR,
            list[clickIndex + 1].title
        ) else ""
    }


    /**
     * 更改播放链接
     */
    private fun changePlayUrl(position: Int) {
        clickIndex = position
        setPlayerPreNextTag()
        val bean = dramaAdapter?.getItem(position) as AnimeDescDetailsBean
        Jzvd.releaseAllVideos()
        alertDialog = Utils.getProDialog(this@PlayerActivity, R.string.parsing)
        val materialButton =
            dramaAdapter!!.getViewByPosition(
                position,
                R.id.tag_group
            ) as MaterialButton
        materialButton.setTextColor(resources.getColor(R.color.tabSelectedTextColor))
        bean.selected = true
        EventBus.getDefault().post(Event(position))
        siteUrl = VideoUtils.getSiteUrl(bean.url)
        witchTitle = animeTitle + " - " + bean.title
        binding.player.playingShow()
        playAnime(siteUrl!!)
    }

    /**
     * 播放视频
     * @param animeUrl
     */
    private fun playAnime(animeUrl: String) {
        //关闭对话框
        Utils.cancelDialog(alertDialog)
        url = animeUrl
        Jzvd.releaseAllVideos()
        binding.player.currentSpeedIndex = 1
        binding.player.setUp(url, witchTitle, Jzvd.SCREEN_FULLSCREEN, JZExoPlayer::class.java)
        binding.player.startVideo()
    }

    /**
     * 播放完
     *
     */
    override fun complete() {
        if (hasNextVideo) {
            videoApplication.showSuccessToastMsg("开始播放下一集")
            clickIndex++
            changePlayUrl(clickIndex)
        } else {
            videoApplication.showSuccessToastMsg("全部播放完毕")
            if (!binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerLayout.openDrawer(GravityCompat.END)
            }
        }
    }

    /**
     * 隐藏虚拟导航按键
     */
    private fun hideNavBar() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    /**
     * Android 9 异形屏适配
     */
    protected open fun hideGap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
        }
    }

    open fun gtSdk23(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    override fun touch() {
        hideNavBar()
    }

    override fun showOrHideChangeView() {
        binding.player.preVideo?.visibility = if (hasPreVideo) View.VISIBLE else View.GONE
        binding.player.nextVideo?.visibility = if (hasNextVideo) View.VISIBLE else View.GONE
    }
}