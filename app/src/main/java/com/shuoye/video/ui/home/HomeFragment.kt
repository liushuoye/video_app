package com.shuoye.video.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.shuoye.video.adapters.BannerDataAdapters
import com.shuoye.video.database.pojo.Banner
import com.shuoye.video.databinding.FragmentHometBinding
import com.shuoye.video.ui.home.adapters.ImageBannerAdapter
import com.shuoye.video.utils.Utils
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


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

        // 初始化轮播图
        setBannerAdapter(binding)
        //初始化新番时间表
        initTimeLine(binding)
        return binding.root
    }

    /**
     * 设置轮播图适配器
     */
    private fun setBannerAdapter(binding: FragmentHometBinding) {

        binding.banner
            // 添加生命周期观察者
            .addBannerLifecycleObserver(this)
            // 设置轮播指示器(显示在banner上)
            .setIndicator(CircleIndicator(context))
            .setLoopTime(4000)

        viewModel.getBannerLiveData().observe(viewLifecycleOwner, {
            viewModel.banner.clear()
            it.data?.let { it1 -> viewModel.banner.addAll(it1) }
            lifecycleScope.launch {
                binding.banner.setAdapter(ImageBannerAdapter(viewModel.banner))
                    // 设置点击事件
                    .setOnBannerListener(OnBannerListener() { banner: Banner, _: Int ->
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToAnimeInfoFragment(banner.id)
                        binding.root.findNavController().navigate(action)
                    })
            }
        })
    }

    /**
     * 初始化番剧更新时间表
     */
    private fun initTimeLine(binding: FragmentHometBinding) {
        binding.viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return TimeLineFragment((position + 1) % 7)
            }

            override fun getItemCount(): Int {
                return 7
            }
        }
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager2
        ) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.text = "一"
                1 -> tab.text = "二"
                2 -> tab.text = "三"
                3 -> tab.text = "四"
                4 -> tab.text = "五"
                5 -> tab.text = "六"
                6 -> tab.text = "日"
            }
        }.attach()
        val time = System.currentTimeMillis()
        val date = Date(time)
        binding.tabLayout.getTabAt(Utils.getWeekOfDate(date))?.select()

    }
}

