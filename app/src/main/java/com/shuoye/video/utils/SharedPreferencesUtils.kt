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
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    fun setParam(context: Context, key: String?, any: Any) {
        val type = any.javaClass.simpleName
        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        when (type) {
            "String" -> {
                editor.putString(key, any as String)
            }
            "Integer" -> {
                editor.putInt(key, (any as Int))
            }
            "Boolean" -> {
                editor.putBoolean(key, (any as Boolean))
            }
            "Float" -> {
                editor.putFloat(key, (any as Float))
            }
            "Long" -> {
                editor.putLong(key, (any as Long))
            }
        }
        editor.apply()
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    fun getParam(context: Context, key: String?, any: Any): Any? {
        val type = any.javaClass.simpleName
        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        when (type) {
            "String" -> {
                return sp.getString(key, any as String)
            }
            "Integer" -> {
                return sp.getInt(key, (any as Int))
            }
            "Boolean" -> {
                return sp.getBoolean(key, (any as Boolean))
            }
            "Float" -> {
                return sp.getFloat(key, (any as Float))
            }
            "Long" -> {
                return sp.getLong(key, (any as Long))
            }
            else -> return null
        }
    }

}