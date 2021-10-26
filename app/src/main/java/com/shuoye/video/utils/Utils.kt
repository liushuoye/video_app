package com.shuoye.video.utils

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.shuoye.video.R
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
    fun getString(activity: Activity, @StringRes id: Int): String {
        return activity.baseContext.resources.getString(id)
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
        msg.setText(getString(activity, id))
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


}