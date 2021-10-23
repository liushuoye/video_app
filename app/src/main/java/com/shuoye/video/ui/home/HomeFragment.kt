package com.shuoye.video.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuoye.video.TAG
import com.shuoye.video.adapters.BannerDataAdapters
import com.shuoye.video.database.pojo.Banner
import com.shuoye.video.databinding.FragmentHometBinding
import com.shuoye.video.ui.home.adapters.ImageBannerAdapter
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var adapter: BannerDataAdapters? = null
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHometBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.timeline.layoutManager = LinearLayoutManager(context)

        adapter = BannerDataAdapters(viewModel.banner)
        binding.banner.setAdapter(ImageBannerAdapter(viewModel.banner))
        binding.banner
            //添加生命周期观察者
            .addBannerLifecycleObserver(this)
            //设置轮播指示器(显示在banner上)
            .setIndicator(CircleIndicator(context))
            .setLoopTime(4000)
            //设置点击事件
            .setOnBannerListener(OnBannerListener<Banner>() { banner: Banner?, position: Int ->
                Log.d(TAG, "onCreateView: 你点击了第${position}张图${banner?.title}")
            })
        binding.timeline.adapter = adapter
        lifecycleScope.launch {
            viewModel.getBanner().collectLatest {
                adapter?.submitData(it)
            }
        }
        return binding.root
    }
}