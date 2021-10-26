package com.shuoye.video.utils

import android.content.Context

/**
 * TODO
 * @program Video
 * @ClassName SharedPreferencesUtils
 * @author shuoye
 * @create 2021-10-26 00:25
 **/
object SharedPreferencesUtils {

    private const val FILE_NAME = "VideoData"


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    fun getParam(context: Context, key: String?, defaultObject: Any): Any? {
        val type = defaultObject.javaClass.simpleName
        val sp =
            context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        if ("String" == type) {
            return sp.getString(key, defaultObject as String)
        } else if ("Integer" == type) {
            return sp.getInt(key, (defaultObject as Int))
        } else if ("Boolean" == type) {
            return sp.getBoolean(key, (defaultObject as Boolean))
        } else if ("Float" == type) {
            return sp.getFloat(key, (defaultObject as Float))
        } else if ("Long" == type) {
            return sp.getLong(key, (defaultObject as Long))
        }
        return null
    }

}