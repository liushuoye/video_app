package com.shuoye.video.fragment.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayoutMediator;
import com.shuoye.video.R;
import com.shuoye.video.databinding.FragmentHomeBinding;
import com.shuoye.video.utils.Utils;
import com.youth.banner.indicator.CircleIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;


/**
 * 首页
 *
 * @author shuoye
 */
public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    Handler.Callback callback;
    NavController navController;
    HomeViewModel model;
    private static final String TAG = "Liu_shuoye";
    private static final String[] TABS = Utils.getArray(R.array.week_array);

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        model = new ViewModelProvider(this).get(HomeViewModel.class);
        //获取导航控制器
        navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
        //TODO 设置点击跳转
        //binding.animeList.setOnClickListener(v -> navController.navigate(R.id.action_homeFragment_to_animeListFragment));
        callback = msg -> {
            if (msg.what == R.id.setBannerAdapter) {
                //设置轮播图适配器
                setBannerAdapter();
            } else if (msg.what == R.id.setTimeLine) {
                //设置新番时间表
                setTimeLine();
            }
            return true;
        };

        if (model.getCallback() == null) {
            model.setCallback(callback);
        } else {
            //设置轮播图适配器
            setBannerAdapter();
            //更新番剧列表
            setTimeLine();
        }
        return binding.getRoot();
    }

    /**
     * 设置轮播图适配器
     */
    void setBannerAdapter() {
        binding.banner.setAdapter(new ImageAdapter(model.getDataBeans()))
                .addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(getContext()))
                .setLoopTime(4000);
        binding.banner.setOnBannerListener((data, position) -> {
            Log.d(TAG, data.getTitle());
            Bundle bundle = new Bundle();
            bundle.putInt("id", data.getAID());
            //TODO 点击跳转到详情
//            navController.navigate(R.id.action_homeFragment_to_animeInfoFragment, bundle);
        });//设置点击事件

    }

    /**
     * 设置新番时间表
     */
    void setTimeLine() {
        binding.viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NotNull
            @Override
            public Fragment createFragment(int position) {
                return new TimeLineFragment(model.getTimeLines().get(position));
            }

            @Override
            public int getItemCount() {
                return 7;
            }
        });

        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, (tab, position) -> tab.setText(TABS[position])).attach();

        long time = System.currentTimeMillis();
        Date date = new Date(time);
        Objects.requireNonNull(binding.tabLayout.getTabAt(Utils.getWeekOfDate(date))).select();

    }
}