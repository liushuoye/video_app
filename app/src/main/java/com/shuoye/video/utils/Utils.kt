package com.shuoye.video.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.browser.customtabs.CustomTabsIntent
import com.shuoye.video.R
import com.shuoye.video.VideoApplication
import java.util.*

/**
 * TODO
 * @program Video
 * @ClassName Utils
 * @author shuoye
 * @create 2021-10-24 17:12
 **/
object Utils {
    /**
     * 获取当前日期是星期几
     *
     * @return + 1 当前日期是星期几
     */
    fun getWeekOfDate(data: Date): Int {
        val weekDays = intArrayOf(6, 0, 1, 2, 3, 4, 5)
        val cal = Calendar.getInstance()
        cal.time = data
        var w = cal[Calendar.DAY_OF_WEEK] - 1
        if (w < 0) {
            w = 0
        }
        return weekDays[w]
    }


    // 两次点击按钮之间的点击间隔不能少于500毫秒
    private const val MIN_CLICK_DELAY_TIME = 500
    private var lastClickTime: Long = 0

    /**
     * 是快速点击
     */
    fun isFastClick(): Boolean {
        var flag = false
        val curClickTime = System.currentTimeMillis()
        if (curClickTime - lastClickTime >= MIN_CLICK_DELAY_TIME) {
            flag = true
        }
        lastClickTime = curClickTime
        return flag
    }

    /**
     * 获取文本
     */
    fun getString(@StringRes id: Int): String {
        return VideoApplication.getInstance().baseContext.resources.getString(id)
    }

    /**
     * 加载框
     *
     * @return
     */
    fun getProDialog(activity: Activity, @StringRes id: Int): AlertDialog {
        val alertDialog: AlertDialog
        val builder = AlertDialog.Builder(activity, R.style.DialogStyle)
        val view: View = LayoutInflater.from(activity).inflate(R.layout.dialog_proress, null)
        val root = view.findViewById<RelativeLayout>(R.id.root)
        val msg = view.findViewById<TextView>(R.id.msg)
        root.setBackgroundColor(activity.resources.getColor(R.color.window_bg))
        msg.setTextColor(activity.resources.getColor(R.color.text_color_primary))
        msg.setText(getString(id))
        builder.setCancelable(false)
        alertDialog = builder.setView(view).create()
        alertDialog.show()
        return alertDialog
    }

    /**
     * 关闭对话框
     * @param alertDialog
     */
    fun cancelDialog(alertDialog: AlertDialog?) {
        alertDialog?.dismiss()
    }

    fun getArray(@ArrayRes id: Int): Array<String> {

        return VideoApplication.getInstance().baseContext.resources.getStringArray(id)
    }

    /**
     * 选择视频播放器
     *
     */
    fun selectVideoPlayer(context: Context, url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setDataAndType(Uri.parse(url), "video/*")
        try {
            context.startActivity(Intent.createChooser(intent, "请选择视频播放器"))
        } catch (e: ActivityNotFoundException) {
            VideoApplication.getInstance().showToastMsg("没有找到匹配的程序")
        }
    }

    /**
     * 通过浏览器打开
     * @param context
     * @param url
     */
    fun viewInChrome(context: Context, url: String?) {
        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        //设置工具栏颜色。
        builder.setToolbarColor(context.resources.getColor(R.color.night))
        val closeBitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.baseline_close_white_48dp)
        builder.setCloseButtonIcon(closeBitmap) // 关闭按钮
        builder.setShowTitle(true) //显示网页标题
        val customTabsIntent: CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}