package com.shuoye.video.fragment.info;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.shuoye.video.databinding.FragmentInfoBinding;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author shuoye
 * @program Video
 * @ClassName InfoFragment
 * @create 2021-10-16 23:21
 **/
public class InfoFragment extends Fragment {
    FragmentInfoBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater);

        return binding.getRoot();
    }

    /**
     * 初始化视图
     */
    private void initView() {
//        JSONObject animeInfo = null;
//        //图片视图
//        Glide.with(binding.imageView)
//                //图片链接
//                .load(animeInfo.getString("cover"))
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
//                .into(binding.imageView);
//        binding.name.setText(animeInfo.getString("name"));
//        binding.state.setText(animeInfo.getString("zt"));
//        binding.tag.setText(animeInfo.getString("tag"));
//        binding.sign.setText("介绍：" + animeInfo.getString("sign"));
//
//        //时间戳处理
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
//        int i = animeInfo.getInteger("time");
//        String time = sdr.format(new Date(i * 1000L)) + "开播";
//        binding.time.setText(time);
    }
}
