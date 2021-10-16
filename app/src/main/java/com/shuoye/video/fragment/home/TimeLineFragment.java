package com.shuoye.video.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.shuoye.video.databinding.FragmentTimeLineBinding;
import com.shuoye.video.pojo.TimeLine;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * 新番时间表
 *
 * @author shuoye
 */
public class TimeLineFragment extends Fragment {
    private final List<TimeLine> data;
    FragmentTimeLineBinding binding;
    TimeLineAdapter adapter;

    public TimeLineFragment(List<TimeLine> data) {
        this.data = data;
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTimeLineBinding.inflate(inflater);
        adapter = new TimeLineAdapter(data);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }
}