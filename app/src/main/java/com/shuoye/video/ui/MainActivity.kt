package com.shuoye.video.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.shuoye.video.R
import com.shuoye.video.databinding.ActivityMainBinding
import com.shuoye.video.utils.StatusBarUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        // 设置状态栏颜色
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }
}