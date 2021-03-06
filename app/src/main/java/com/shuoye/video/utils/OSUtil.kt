package com.shuoye.video.utils

import android.os.Environment
import android.text.TextUtils
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*


/**
 * TODO
 * @program Video
 * @ClassName OSUtil
 * @author shuoye
 * @create 2021-11-06 21:18
 **/
object OSUtil {
    //MIUI标识
    private const val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code"
    private const val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private const val KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage"

    //EMUI标识
    private const val KEY_EMUI_VERSION_CODE = "ro.build.version.emui"
    private const val KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level"
    private const val KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion"

    //Flyme标识
    private const val KEY_FLYME_ID_FALG_KEY = "ro.build.display.id"
    private const val KEY_FLYME_ID_FALG_VALUE_KEYWORD = "Flyme"
    private const val KEY_FLYME_ICON_FALG = "persist.sys.use.flyme.icon"
    private const val KEY_FLYME_SETUP_FALG = "ro.meizu.setupwizard.flyme"
    private const val KEY_FLYME_PUBLISH_FALG = "ro.flyme.published"

    /**
     * 是否是Flyme系统
     */
    fun isFlyme(): Boolean {
        if (isPropertiesExist(KEY_FLYME_ICON_FALG, KEY_FLYME_SETUP_FALG, KEY_FLYME_PUBLISH_FALG)) {
            return true
        }
        try {
            val buildProperties = BuildProperties.newInstance()
            if (buildProperties.containsKey(KEY_FLYME_ID_FALG_KEY)) {
                val romName = buildProperties.getProperty(KEY_FLYME_ID_FALG_KEY)
                if (!TextUtils.isEmpty(romName) && romName.contains(KEY_FLYME_ID_FALG_VALUE_KEYWORD)) {
                    return true
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 是否是EMUI系统
     */
    fun isEMUI(): Boolean {
        return isPropertiesExist(
            KEY_EMUI_VERSION_CODE, KEY_EMUI_API_LEVEL,
            KEY_EMUI_CONFIG_HW_SYS_VERSION
        )
    }

    /**
     * 是否是MIUI系统
     */
    fun isMIUI(): Boolean {
        return isPropertiesExist(
            KEY_MIUI_VERSION_CODE, KEY_MIUI_VERSION_NAME,
            KEY_MIUI_INTERNAL_STORAGE
        )
    }

    private fun isPropertiesExist(vararg keys: String): Boolean {
        return if (keys.isEmpty()) {
            false
        } else try {
            val properties = BuildProperties.newInstance()
            for (key in keys) {
                properties.getProperty(key)
                return true
            }
            false
        } catch (e: IOException) {
            false
        }
    }

    private class BuildProperties private constructor() {
        private val properties: Properties = Properties()
        fun containsKey(key: Any?): Boolean {
            return properties.containsKey(key)
        }

        fun containsValue(value: Any?): Boolean {
            return properties.containsValue(value)
        }

        fun entrySet(): Set<Map.Entry<Any, Any>> {
            return properties.entries
        }

        fun getProperty(name: String?): String {
            return properties.getProperty(name)
        }

        fun getProperty(name: String?, defaultValue: String?): String {
            return properties.getProperty(name, defaultValue)
        }

        val isEmpty: Boolean
            get() = properties.isEmpty

        fun keys(): Enumeration<Any> {
            return properties.keys()
        }


        fun size(): Int {
            return properties.size
        }

        fun values(): Collection<Any> {
            return properties.values
        }

        companion object {
            @Throws(IOException::class)
            fun newInstance(): BuildProperties {
                return BuildProperties()
            }
        }

        init {
            // 读取系统配置信息build.prop类
            properties.load(
                FileInputStream(File(Environment.getRootDirectory(), "build.prop"))
            )
        }
    }
}