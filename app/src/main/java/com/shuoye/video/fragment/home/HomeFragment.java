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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shuoye.video.R;
import com.shuoye.video.databinding.FragmentHomeBinding;
import com.shuoye.video.pojo.DataBean;
import com.shuoye.video.utils.Utils;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    Handler.Callback callback;
    NavController navController;//导航控制器
    HomeViewModel model;
    private static final String TAG = "shuoye";
    private static final String[] TABS = Utils.getArray(R.array.week_array);

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        model = new ViewModelProvider(this).get(HomeViewModel.class);
        navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);//获取导航控制器
//        binding.animeList.setOnClickListener(v -> navController.navigate(R.id.action_homeFragment_to_animeListFragment));
        callback = msg -> {
            if (msg.what == 1) {
                //设置轮播图适配器
                setBannerAdapter();
            } else if (msg.what == 2) {
                //更新番剧列表
                renewal();
            }
            return true;
        };

        if (model.getCallback() == null)
            model.setCallback(callback);
        else {
            //设置轮播图适配器
            setBannerAdapter();
            //更新番剧列表
            renewal();
        }


        return binding.getRoot();
    }

    /**
     * 设置轮播图适配器
     */
    void setBannerAdapter() {
        binding.banner.setAdapter(new BannerImageAdapter<DataBean>(model.getDataBeanList()) {
            @Override
            public void onBindView(BannerImageHolder holder, DataBean data, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)//图片视图
                        .load(data.imageUrl)//图片链接
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        })
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getContext()))//设置轮播指示器(显示在banner上)
                .setLoopTime(4000)//设置轮播间隔时间 单位毫秒
                .setOnBannerListener((OnBannerListener<DataBean>) (data, position) -> {
                    Log.d(TAG, data.title);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", data.title);
//                    navController.navigate(R.id.action_homeFragment_to_animeInfoFragment, bundle);
                });//设置点击事件
    }

    void renewal() {
        binding.viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NotNull
            @Override
            public Fragment createFragment(int position) {
//                return new RenewalFragment(model.getDataRenew().get(position));
                return new Fragment();
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