package com.shuoye.video.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.ArrayRes;

import java.util.Calendar;
import java.util.Date;


public class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("不能实例化…");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("应该先初始化");
    }

    /**
     * 获取当前日期是星期几
     *
     * @return + 1 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        int[] weekDays = {6, 0, 1, 2, 3, 4, 5};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取字符串数组
     *
     * @param id int
     * @return String[]
     */
    public static String[] getArray(@ArrayRes int id) {
        return getContext().getResources().getStringArray(id);
    }
}
