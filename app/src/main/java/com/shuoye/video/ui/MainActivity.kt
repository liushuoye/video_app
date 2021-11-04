package com.shuoye.video.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.jaeger.library.StatusBarUtil
import com.shuoye.video.R
import com.shuoye.video.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        //设置状态栏全透明
//        StatusBarUtil.setTransparent(this)
        // 设置状态栏半透明
//        StatusBarUtil.setTranslucent(this, 100)
        // 设置状态栏颜色
        StatusBarUtil.setColor(this, R.color.purple50)
    }
}