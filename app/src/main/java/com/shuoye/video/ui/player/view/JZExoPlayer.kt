package com.shuoye.video.ui.player.view

import android.graphics.SurfaceTexture
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.view.Surface
import cn.jzvd.JZMediaInterface
import cn.jzvd.Jzvd
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultAllocator
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.shuoye.video.R

class JZExoPlayer(jzvd: Jzvd?) : JZMediaInterface(jzvd), Player.EventListener,
    Player.Listener {
    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var callback: Runnable? = null
    private val TAG = "JZMediaExo"
    private var previousSeek: Long = 0
    override fun start() {
        simpleExoPlayer?.setPlayWhenReady(true)
    }

    override fun prepare() {
        Log.e(TAG, "prepare")
        val context = jzvd.context
        release()
        mMediaHandlerThread = HandlerThread("JZVD")
        mMediaHandlerThread.start()
//        mMediaHandler = Handler(mMediaHandlerThread.looper) //主线程还是非主线程，就在这里
        mMediaHandler = Handler(Looper.myLooper()!!) //主线程还是非主线程，就在这里
        handler = Handler(Looper.myLooper()!!)

        mMediaHandler.post {
            val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter.Builder(context).build()
            val videoTrackSelectionFactory: AdaptiveTrackSelection.Factory =
                AdaptiveTrackSelection.Factory()

            val trackSelector: TrackSelector =
                DefaultTrackSelector(context, videoTrackSelectionFactory)

            val loadControl: LoadControl = DefaultLoadControl.Builder()
                .setPrioritizeTimeOverSizeThresholds(false)
                .setBufferDurationsMs(360000, 600000, 1000, 5000)
                .setTargetBufferBytes(C.LENGTH_UNSET)
                .setAllocator(DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE))
                .setBackBuffer(1000, true)
                .build()

            // 2.创建播放器
            val renderersFactory: RenderersFactory = DefaultRenderersFactory(context)
            simpleExoPlayer = SimpleExoPlayer.Builder(context, renderersFactory)
                .setTrackSelector(trackSelector)
                .setBandwidthMeter(bandwidthMeter)
                .setLoadControl(loadControl)
                .build()


            val currUrl = jzvd.jzDataSource.currentUrl.toString()
            val videoSource: MediaSource
            val httpDataSourceFactory = DefaultHttpDataSource.Factory()
                .setUserAgent(
                    Util.getUserAgent(
                        context,
                        context.resources.getString(R.string.app_name)
                    )
                )
                .setTransferListener(null)
                .setConnectTimeoutMs(1500)
                .setReadTimeoutMs(1500)
                //允许跨协议重定向
                .setAllowCrossProtocolRedirects(true)

            if (currUrl.contains(".m3u8")) {
                videoSource = HlsMediaSource.Factory(httpDataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(currUrl)))
                videoSource.addDrmEventListener(handler, null!!)
            } else {
                videoSource = ProgressiveMediaSource.Factory(httpDataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(currUrl)))
            }
            simpleExoPlayer!!.addVideoListener(this)
            Log.e(TAG, "URL Link = $currUrl")
            simpleExoPlayer!!.addListener(this)
            val isLoop = jzvd.jzDataSource.looping
            if (isLoop) {
                simpleExoPlayer!!.setRepeatMode(Player.REPEAT_MODE_ONE)
            } else {
                simpleExoPlayer!!.setRepeatMode(Player.REPEAT_MODE_OFF)
            }
            simpleExoPlayer!!.prepare(videoSource)
            simpleExoPlayer!!.setPlayWhenReady(true)
            callback = onBufferingUpdate()
            simpleExoPlayer!!.setVideoSurface(Surface(jzvd.textureView.surfaceTexture))
//            simpleExoPlayer!!.setThrowsWhenUsingWrongThread(false)

        }
    }

    override fun onVideoSizeChanged(
        width: Int,
        height: Int,
        unappliedRotationDegrees: Int,
        pixelWidthHeightRatio: Float
    ) {
        handler.post { jzvd.onVideoSizeChanged(width, height) }
    }

    override fun onRenderedFirstFrame() {
        Log.e(TAG, "onRenderedFirstFrame")
    }

    override fun pause() {
        simpleExoPlayer?.setPlayWhenReady(false)
    }

    override fun isPlaying(): Boolean {
        return simpleExoPlayer!!.getPlayWhenReady()
    }

    override fun seekTo(time: Long) {
        if (time != previousSeek) {
            simpleExoPlayer?.seekTo(time)
            previousSeek = time
            jzvd.seekToInAdvance = time
        }
    }

    override fun release() {
        if (mMediaHandler != null && mMediaHandlerThread != null && simpleExoPlayer != null) {
            val tmpHandlerThread = mMediaHandlerThread
            val tmpMediaPlayer: SimpleExoPlayer? = simpleExoPlayer
            SAVED_SURFACE = null
            mMediaHandler.post {
                tmpMediaPlayer?.release() //release就不能放到主线程里，界面会卡顿
                tmpHandlerThread.quit()
            }
            simpleExoPlayer = null
        }
    }

    override fun getCurrentPosition(): Long {
        return if (simpleExoPlayer != null) {
            simpleExoPlayer!!.getCurrentPosition()
        } else {
            0
        }
    }

    override fun getDuration(): Long {
        return if (simpleExoPlayer != null) {
            simpleExoPlayer!!.getDuration()
        } else {
            0
        }
    }

    override fun setVolume(leftVolume: Float, rightVolume: Float) {
        simpleExoPlayer?.setVolume(leftVolume)
        simpleExoPlayer?.setVolume(rightVolume)
    }

    override fun setSpeed(speed: Float) {
        val playbackParameters = PlaybackParameters(speed, 1.0f)
        simpleExoPlayer?.setPlaybackParameters(playbackParameters)
    }

    fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
        Log.e(TAG, "onTimelineChanged")
        //        JZMediaPlayer.instance().mainThreadHandler.post(() -> {
//                if (reason == 0) {
//
//                    JzvdMgr.getCurrentJzvd().onInfo(reason, timeline.getPeriodCount());
//                }
//        });
    }

    //    fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {}
    override fun onLoadingChanged(isLoading: Boolean) {
        Log.e(TAG, "onLoadingChanged")
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        Log.e(TAG, "onPlayerStateChanged$playbackState/ready=$playWhenReady")
        handler.post {
            when (playbackState) {
                Player.STATE_IDLE -> {
                }
                Player.STATE_BUFFERING -> {
                    handler.post(callback!!)
                }
                Player.STATE_READY -> {
                    if (playWhenReady) {
                        jzvd.onStatePlaying()
                    } else {
                    }
                }
                Player.STATE_ENDED -> {
                    jzvd.onCompletion()
                }
            }
        }
    }

    override fun onRepeatModeChanged(repeatMode: Int) {}
    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
    fun onPlayerError(error: ExoPlaybackException) {
        Log.e(TAG, "onPlayerError" + error.toString())
        handler.post { jzvd.onError(1000, 1000) }
    }

    override fun onPositionDiscontinuity(reason: Int) {}

    //    fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {}
    override fun onSeekProcessed() {
        handler.post { jzvd.onSeekComplete() }
    }

    override fun setSurface(surface: Surface) {
        simpleExoPlayer?.setVideoSurface(surface)
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        if (SAVED_SURFACE == null) {
            SAVED_SURFACE = surface
            prepare()
        } else {
            jzvd.textureView.setSurfaceTexture(SAVED_SURFACE)
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}
    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        return false
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
    private inner class onBufferingUpdate : Runnable {
        override fun run() {
            if (simpleExoPlayer != null) {
                val percent: Int = simpleExoPlayer!!.bufferedPercentage
                handler.post { jzvd.setBufferProgress(percent) }
                if (percent < 100) {
                    handler.postDelayed(callback!!, 300)
                } else {
                    handler.removeCallbacks(callback!!)
                }
            }
        }
    }
}