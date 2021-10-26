package com.shuoye.video

import android.app.Application
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp
import es.dmoral.toasty.Toasty

/**
 * 应用上下文
 * @program video
 * @ClassName VideoApplication
 * @author shuoye
 * @create 2021-10-21 17:05
 **/
@HiltAndroidApp
class VideoApplication : Application() {
    companion object {
        @Volatile
        private var instance: VideoApplication? = null

        fun getInstance(): VideoApplication {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    /**
     * 显示成功 Toast 消息
     */
    fun showSuccessToastMsg(msg: String) {
        Toasty.success(applicationContext, msg, Toast.LENGTH_LONG, true).show()
    }

    /**
     * 显示吐司消息
     */
    fun showToastMsg(msg: String?) {
        Toasty.warning(applicationContext, msg!!, Toast.LENGTH_LONG, true).show()
    }
}