package com.shuoye.video.ui.player

import android.app.PictureInPictureParams
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.navigation.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.google.android.material.button.MaterialButton
import com.shuoye.video.R
import com.shuoye.video.VideoApplication
import com.shuoye.video.database.pojo.Player
import com.shuoye.video.databinding.ActivityPlayBinding
import com.shuoye.video.ui.player.adapter.AnimeDescDramaAdapter
import com.shuoye.video.ui.player.bean.Event
import com.shuoye.video.ui.player.view.JZExoPlayer
import com.shuoye.video.ui.player.view.JZPlayer
import com.shuoye.video.utils.SharedPreferencesUtils
import com.shuoye.video.utils.Utils
import com.shuoye.video.utils.VideoUtils
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import java.util.*


/**
 * 视频播放器
 * @program Video
 * @ClassName PlayerActivity
 * @author shuoye
 * @create 2021-10-26 02:59
 **/
@AndroidEntryPoint
open class PlayerActivity : AppCompatActivity(), JZPlayer.CompleteListener, JZPlayer.TouchListener,
    JZPlayer.ShowOrHideChangeViewListener {
    private val model: PlayerViewModel by viewModels()
    private val args: PlayerActivityArgs by navArgs()

    companion object {
        private const val PREVIDEOSTR = "上一集：%s"
        private const val NEXTVIDEOSTR = "下一集：%s"
    }

    private val videoApplication = VideoApplication.getInstance()
    private lateinit var binding: ActivityPlayBinding
    private lateinit var speeds: Array<String>


    // 有上一个视频
    private var hasPreVideo = false

    // 有下一个视频
    private var hasNextVideo = false


    //展开播放列表适配器
    private var dramaAdapter: AnimeDescDramaAdapter? = null

    //警报对话框
    private var alertDialog: AlertDialog? = null

    //用户播放速度下标
    private var userSpeed = 2

    private val isPip = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play)
        speeds = Utils.getArray(R.array.speed_item)

        hideNavBar()
        hideGap()

        model.getPlayers(args.animeInfoId).observe(this, { resource ->
            model.players.clear()
            resource.data?.let { players ->
                model.players.addAll(players)
                if (model.players.isNotEmpty()) {
                    model.players[0].let { player ->
                        model.url = player.url
                        model.siteUrl = player.playVid
                        model.witchTitle = player.titleL
                        model.clickIndex = 0
                        model.animeTitle = args.animeInfoTitle
                    }
                    initPlayer()
                    initAdapter()
                    initUserConfig()
                    initOnClick()
                }
            }

        })
    }

    /**
     * 初始化播放器
     */
    private fun initPlayer() {

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
            model.clickIndex--
            changePlayUrl(model.clickIndex)
        }
        //播放下一个视频
        binding.player.nextVideo?.setOnClickListener {
            model.clickIndex++
            changePlayUrl(model.clickIndex)
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
            model.url,
            "${model.animeTitle} - ${model.witchTitle}",
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
        dramaAdapter = AnimeDescDramaAdapter(this, model.players)
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
        hasPreVideo = model.clickIndex != 0
        binding.player.preVideo?.text = if (hasPreVideo) String.format(
            PREVIDEOSTR,
            model.players[model.clickIndex - 1].title
        ) else ""
        hasNextVideo = model.clickIndex != model.players.size - 1
        binding.player.nextVideo?.text = if (hasNextVideo) String.format(
            NEXTVIDEOSTR,
            model.players[model.clickIndex + 1].title
        ) else ""
    }


    /**
     * 更改播放链接
     */
    private fun changePlayUrl(position: Int) {
        model.clickIndex = position
        setPlayerPreNextTag()
        val bean = dramaAdapter?.getItem(position) as Player
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
        model.siteUrl = bean.url?.let { VideoUtils.getSiteUrl(it) }
        model.witchTitle = model.animeTitle + " - " + bean.title
        binding.player.playingShow()
        playAnime(model.siteUrl!!)
    }

    /**
     * 播放视频
     */
    private fun playAnime(animeUrl: String) {
        //关闭对话框
        Utils.cancelDialog(alertDialog)
        model.url = animeUrl
        Jzvd.releaseAllVideos()
        binding.player.currentSpeedIndex = 1
        binding.player.setUp(
            model.url,
            model.witchTitle,
            Jzvd.SCREEN_FULLSCREEN,
            JZExoPlayer::class.java
        )
        binding.player.startVideo()
    }

    /**
     * 播放完
     *
     */
    override fun complete() {
        if (hasNextVideo) {
            videoApplication.showSuccessToastMsg("开始播放下一集")
            model.clickIndex++
            changePlayUrl(model.clickIndex)
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

    /**
     * 初始化用户配置
     */
    private fun initUserConfig() {
        when (SharedPreferencesUtils.getParam(this, "user_speed", 15)) {
            5 -> setUserSpeedConfig(speeds.get(0), 0)
            10 -> setUserSpeedConfig(speeds.get(1), 1)
            15 -> setUserSpeedConfig(speeds.get(2), 2)
            30 -> setUserSpeedConfig(speeds.get(3), 3)
        }
        binding.hideProgress.isChecked = SharedPreferencesUtils.getParam(
            this,
            "hide_progress",
            false
        ) as Boolean
        binding.hideProgress.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            SharedPreferencesUtils.setParam(this, "hide_progress", isChecked)
        }
    }

    /**
     * 点击初始化
     */
    private fun initOnClick() {
        binding.speedConfig.setOnClickListener { setDefaultSpeed() }
        binding.picConfig.setOnClickListener {
            if (gtSdk26()) {
                // TODO 画中画
//                startPic()
            }
        }
        binding.playerConfig.setOnClickListener {
            model.url?.let { url ->
                Utils.selectVideoPlayer(this, url)
            }
        }
        binding.browserConfig.setOnClickListener { Utils.viewInChrome(this, model.siteUrl) }

    }

    /**
     * 设置用户速度配置
     */
    private fun setUserSpeedConfig(text: String, speed: Int) {
        binding.speed.text = text
        userSpeed = speed
    }

    /**
     * 开始画中画
     */
    @RequiresApi(Build.VERSION_CODES.O)
    open fun startPic() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) binding.drawerLayout.closeDrawer(
            GravityCompat.START
        )
        Handler(Looper.myLooper()!!).postDelayed({ this.enterPicInPic() }, 500)
    }

    /**
     * 设置默认速度
     */
    private fun setDefaultSpeed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(Utils.getString(R.string.set_user_speed))
        builder.setSingleChoiceItems(speeds, userSpeed) { dialog: DialogInterface, which: Int ->
            when (which) {
                0 -> {
                    SharedPreferencesUtils.setParam(applicationContext, "user_speed", 5)
                    setUserSpeedConfig(speeds[0], which)
                }
                1 -> {
                    SharedPreferencesUtils.setParam(applicationContext, "user_speed", 10)
                    setUserSpeedConfig(speeds[1], which)
                }
                2 -> {
                    SharedPreferencesUtils.setParam(applicationContext, "user_speed", 15)
                    setUserSpeedConfig(speeds[2], which)
                }
                3 -> {
                    SharedPreferencesUtils.setParam(applicationContext, "user_speed", 30)
                    setUserSpeedConfig(speeds[3], which)
                }
            }
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    /**
     * Android 8.0 画中画
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun enterPicInPic() {
//        PictureInPictureParams.Builder builder = new PictureInPictureParams.Builder();
        // 设置宽高比例值，第一个参数表示分子，第二个参数表示分母
        // 下面的10/5=2，表示画中画窗口的宽度是高度的两倍
//        Rational aspectRatio = new Rational(10,5);
        // 设置画中画窗口的宽高比例
//        builder.setAspectRatio(aspectRatio);
        // 进入画中画模式，注意enterPictureInPictureMode是Android8.0之后新增的方法
//        enterPictureInPictureMode(builder.build());
        val builder = PictureInPictureParams.Builder().build()
        enterPictureInPictureMode(builder)
    }

    /**
     * 后退按下
     */
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else finish()
    }

    /**
     * 暂停时
     */
    override fun onPause() {
        super.onPause()
        Jzvd.goOnPlayOnPause()
    }

    /**
     * 恢复
     */
    override fun onResume() {
        super.onResume()
        hideNavBar()
        if (!inMultiWindow()) {
            Jzvd.goOnPlayOnResume()
        }
    }

    /**
     * 是否为分屏模式
     *
     * @return
     */
    open fun inMultiWindow(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) this.isInMultiWindowMode else false
    }

    /**
     * 停止时
     */
    override fun onStop() {
        super.onStop()
        if (isPip) finish()
    }

    /**
     * 在销毁
     */
    override fun onDestroy() {
        JzvdStd.releaseAllVideos()
        super.onDestroy()
    }

    /**
     * sdk>=23
     */
    open fun gtSdk23(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    /**
     * sdk>=26
     */
    open fun gtSdk26(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    /**
     * 触碰
     */
    override fun touch() {
        hideNavBar()
    }

    /**
     * 结束
     */
    override fun finish() {
        // 释放所有视频
        JzvdStd.releaseAllVideos()
        super.finish()
    }

    /**
     * 显示或隐藏更改视图
     */
    override fun showOrHideChangeView() {
        binding.player.preVideo?.visibility = if (hasPreVideo) View.VISIBLE else View.GONE
        binding.player.nextVideo?.visibility = if (hasNextVideo) View.VISIBLE else View.GONE
    }
}