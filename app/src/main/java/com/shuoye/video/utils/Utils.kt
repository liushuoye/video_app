package com.shuoye.video.utils

import java.util.*

/**
 * TODO
 * @program Video
 * @ClassName Utils
 * @author shuoye
 * @create 2021-10-24 17:12
 **/
class Utils {
    companion object {
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
    }


}