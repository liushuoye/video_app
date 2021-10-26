package com.shuoye.video.ui.player.view

import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import cn.jzvd.JZDataSource
import cn.jzvd.JZUtils
import cn.jzvd.JzvdStd
import com.shuoye.video.R
import com.shuoye.video.utils.SharedPreferencesUtils

class JZPlayer : JzvdStd {
    private var mcontext: Context? = null
    private var listener: CompleteListener? = null
    private var touchListener: TouchListener? = null
    private var showOrHideChangeViewListener: ShowOrHideChangeViewListener? = null
    private var ibLock: ImageView? = null
    private var locked = false
    var fastForward: ImageView? = null
    var quickRetreat: ImageView? = null
    var config: ImageView? = null
    var airplay: ImageView? = null
    var tvSpeed: TextView? = null
    var snifferBtn: TextView? = null
    var openDrama: TextView? = null
    var preVideo: TextView? = null
    var nextVideo: TextView? = null
    var currentSpeedIndex = 1

    constructor(mcontext: Context?) : super(mcontext) {}
    constructor(mcontext: Context?, attrs: AttributeSet?) : super(mcontext, attrs) {}

    fun setListener(
        mcontext: Context?,
        listener: CompleteListener?,
        touchListener: TouchListener?,
        showOrHideChangeViewListener: ShowOrHideChangeViewListener?
    ) {
        this.mcontext = mcontext
        this.listener = listener
        this.touchListener = touchListener
        this.showOrHideChangeViewListener = showOrHideChangeViewListener
    }

    override fun getLayoutId(): Int {
        return R.layout.jz_layout_std
    }

    override fun init(mcontext: Context) {
        super.init(mcontext)
        // 获取自定义添加的控件
        ibLock = findViewById(R.id.std_lock)
        ibLock!!.setOnClickListener(this)
        quickRetreat = findViewById(R.id.quick_retreat)
        quickRetreat!!.setOnClickListener(this)
        fastForward = findViewById(R.id.fast_forward)
        fastForward!!.setOnClickListener(this)
        config = findViewById(R.id.config)
        tvSpeed = findViewById(R.id.tvSpeed)
        tvSpeed!!.setOnClickListener(this)
        airplay = findViewById(R.id.airplay)
        airplay!!.setOnClickListener(this)
        snifferBtn = findViewById(R.id.sniffer_btn)
        openDrama = findViewById(R.id.open_drama_list)
        preVideo = findViewById(R.id.pre_video)
        nextVideo = findViewById(R.id.next_video)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.std_lock -> {
                if (locked) {
                    // 已经上锁，再次点击解锁
                    changeUiToPlayingShow()
                    ibLock!!.setImageResource(R.drawable.player_btn_locking)
                    Toast.makeText(mcontext, "屏幕锁定关闭", Toast.LENGTH_SHORT).show()
                } else {
                    // 上锁
                    changeUiToPlayingClear()
                    ibLock!!.setImageResource(R.drawable.player_btn_locking_pre)
                    Toast.makeText(mcontext, "屏幕锁定开启", Toast.LENGTH_SHORT).show()
                    //                    Drawable up = ContextCompat.getDrawable(mcontext,R.drawable.player_btn_locking_pre);
//                    Drawable drawableUp= DrawableCompat.wrap(up);
//                    DrawableCompat.setTint(drawableUp, ContextCompat.getColor(mcontext,R.color.colorAccent));
//                    ibLock.setImageDrawable(drawableUp);
                }
                locked = !locked
            }
            R.id.fast_forward -> {
                //总时间长度
                val duration = duration
                //当前时间
                val currentPositionWhenPlaying = currentPositionWhenPlaying
                //快进（15S）
                val fastForwardProgress =
                    currentPositionWhenPlaying + SharedPreferencesUtils.getParam(
                        mcontext!!,
                        "user_speed",
                        15
                    ) as Int * 1000
                if (duration > fastForwardProgress) {
                    mediaInterface.seekTo(fastForwardProgress)
                } else {
                    mediaInterface.seekTo(duration)
                }
            }
            R.id.quick_retreat -> {
                //当前时间
                val quickRetreatCurrentPositionWhenPlaying =
                    currentPositionWhenPlaying
                //快退（15S）
                val quickRetreatProgress =
                    quickRetreatCurrentPositionWhenPlaying - SharedPreferencesUtils.getParam(
                        mcontext!!,
                        "user_speed",
                        15
                    ) as Int * 1000
                if (quickRetreatProgress > 0) {
                    mediaInterface.seekTo(quickRetreatProgress)
                } else {
                    mediaInterface.seekTo(0)
                }
            }
            R.id.tvSpeed -> {
                if (currentSpeedIndex == 7) {
                    currentSpeedIndex = 0
                } else {
                    currentSpeedIndex += 1
                }
                mediaInterface.setSpeed(getSpeedFromIndex(currentSpeedIndex))
                tvSpeed!!.text = "倍数X" + getSpeedFromIndex(currentSpeedIndex)
            }
            /*
            R.id.airplay -> {
                val bundle = Bundle()
                Log.e("duration", duration.toString() + "")
                Log.e("playUrl", jzDataSource.currentUrl.toString())
                bundle.putString("playUrl", jzDataSource.currentUrl.toString())
                bundle.putLong("duration", duration)
                mcontext!!.startActivity(
                    Intent(
                        mcontext,
                        DLNAActivity::class.java
                    ).putExtras(bundle)
                )
            }

             */
        }
    }

    private fun getSpeedFromIndex(index: Int): Float {
        var ret = 0f
        when (index) {
            0 -> ret = 0.5f
            1 -> ret = 1.0f
            2 -> ret = 1.25f
            3 -> ret = 1.5f
            4 -> ret = 1.75f
            5 -> ret = 2.0f
            6 -> ret = 2.5f
            7 -> ret = 3.0f
        }
        return ret
    }

    //这里是播放的时候点击屏幕出现的UI
    override fun changeUiToPlayingShow() {
        // 此处做锁屏功能的按钮显示，判断是否锁屏状态，并且需要注意当前屏幕状态
        if (!locked) {
            super.changeUiToPlayingShow()
            fastForward!!.visibility = VISIBLE
            quickRetreat!!.visibility = VISIBLE
            config!!.visibility = VISIBLE
            airplay!!.visibility = VISIBLE
            showOrHideChangeViewListener!!.showOrHideChangeView()
        }
        if (screen == SCREEN_FULLSCREEN) {
            ibLock!!.visibility = if (ibLock!!.visibility == VISIBLE) GONE else VISIBLE
        }
    }

    fun playingShow() {
        setAllControlsVisiblity(
            INVISIBLE, INVISIBLE, INVISIBLE,
            VISIBLE, INVISIBLE, INVISIBLE, INVISIBLE
        )
        ibLock!!.visibility = GONE
        fastForward!!.visibility = GONE
        quickRetreat!!.visibility = GONE
        config!!.visibility = GONE
        airplay!!.visibility = GONE
        preVideo!!.visibility = GONE
        nextVideo!!.visibility = GONE
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        touchListener!!.touch()
        when (event.action) {
            MotionEvent.ACTION_MOVE -> if (locked) {
                return true
            }
        }
        return super.onTouch(v, event)
    }

    //这里是播放的时候屏幕上面UI消失  只显示下面底部的进度条UI
    override fun changeUiToPlayingClear() {
        if (SharedPreferencesUtils.getParam(mcontext!!, "hide_progress", false) as Boolean) {
            setAllControlsVisiblity(
                INVISIBLE, INVISIBLE, INVISIBLE,
                INVISIBLE, INVISIBLE, INVISIBLE, INVISIBLE
            ) // 全屏播放时隐藏底部进度条
        } else {
            super.changeUiToPlayingClear()
        }
        ibLock!!.visibility = INVISIBLE
        fastForward!!.visibility = INVISIBLE
        quickRetreat!!.visibility = INVISIBLE
        config!!.visibility = INVISIBLE
        airplay!!.visibility = INVISIBLE
        preVideo!!.visibility = INVISIBLE
        nextVideo!!.visibility = INVISIBLE
    }

    // 点击暂停按钮执行的回调
    override fun onStatePause() {
        super.onStatePause()
        ibLock!!.visibility = INVISIBLE
        fastForward!!.visibility = INVISIBLE
        quickRetreat!!.visibility = INVISIBLE
        config!!.visibility = INVISIBLE
        airplay!!.visibility = VISIBLE
        preVideo!!.visibility = INVISIBLE
        nextVideo!!.visibility = INVISIBLE
    }

    //这里是暂停的时候点击屏幕消失的UI,只显示下面底部的进度条UI
    override fun changeUiToPauseClear() {
        super.changeUiToPauseClear()
        ibLock!!.visibility = INVISIBLE
        fastForward!!.visibility = INVISIBLE
        quickRetreat!!.visibility = INVISIBLE
        config!!.visibility = INVISIBLE
        airplay!!.visibility = VISIBLE
        preVideo!!.visibility = INVISIBLE
        nextVideo!!.visibility = INVISIBLE
    }

    //这里是出错的UI
    override fun changeUiToError() {
        super.changeUiToError()
        ibLock!!.visibility = INVISIBLE
        fastForward!!.visibility = INVISIBLE
        quickRetreat!!.visibility = INVISIBLE
        config!!.visibility = INVISIBLE
        airplay!!.visibility = INVISIBLE
        preVideo!!.visibility = INVISIBLE
        nextVideo!!.visibility = INVISIBLE
    }

    // 点击屏幕会出现所有控件，一定时间后消失的回调
    override fun dissmissControlView() {
        super.dissmissControlView()
        // 需要在UI线程进行隐藏
        post {
            ibLock!!.visibility = INVISIBLE
            fastForward!!.visibility = INVISIBLE
            quickRetreat!!.visibility = INVISIBLE
            config!!.visibility = INVISIBLE
            airplay!!.visibility = if (state == STATE_ERROR) INVISIBLE else VISIBLE
            if (SharedPreferencesUtils.getParam(
                    mcontext!!,
                    "hide_progress",
                    false
                ) as Boolean
            ) {
                bottomProgressBar.visibility = INVISIBLE // 全屏播放时隐藏底部进度条
            }
            preVideo!!.visibility = INVISIBLE
            nextVideo!!.visibility = INVISIBLE
        }
    }

    override fun setScreenFullscreen() {
        super.setScreenFullscreen()
        fullscreenButton.setImageResource(R.drawable.baseline_view_selections_white_48dp)
        tvSpeed!!.text = "倍数X" + getSpeedFromIndex(currentSpeedIndex)
    }

    interface CompleteListener {
        fun complete()
    }

    interface TouchListener {
        fun touch()
    }

    interface ShowOrHideChangeViewListener {
        fun showOrHideChangeView()
    }

    override fun setUp(jzDataSource: JZDataSource, screen: Int, mediaInterfaceClass: Class<*>?) {
        super.setUp(jzDataSource, screen, mediaInterfaceClass)
        batteryTimeLayout.visibility = GONE
    }

    fun startPIP() {
        changeUiToPlayingClear()
    }

    fun onAutoCompletion() {
        onStateAutoComplete()
        listener!!.complete()
    }

    override fun showWifiDialog() {
        val builder = AlertDialog.Builder(getContext(), R.style.DialogStyle)
        builder.setMessage(resources.getString(R.string.tips_not_wifi))
        builder.setPositiveButton(
            resources.getString(R.string.tips_not_wifi_confirm)
        ) { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
            WIFI_TIP_DIALOG_SHOWED = true
            if (state == STATE_PAUSE) {
                startButton.performClick()
            } else {
                startVideo()
            }
        }
        builder.setNegativeButton(
            resources.getString(R.string.tips_not_wifi_cancel)
        ) { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
            releaseAllVideos()
            val vg =
                JZUtils.scanForActivity(getContext()).window.decorView as ViewGroup
            vg.removeView(this)
            if (mediaInterface != null) {
                mediaInterface.release()
            }
            CURRENT_JZVD = null
        }
        builder.setCancelable(false)
        builder.create().show()
    }
}