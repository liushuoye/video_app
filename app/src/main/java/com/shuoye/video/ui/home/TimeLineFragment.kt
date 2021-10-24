package com.shuoye.video.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuoye.video.databinding.FragmentRecyclerViewBinding
import com.shuoye.video.ui.home.adapters.TimeLineAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * TODO
 * @program Video
 * @ClassName TimeLineFragment
 * @author shuoye
 * @create 2021-10-24 15:20
 **/
@AndroidEntryPoint
class TimeLineFragment(val wd: Int) : Fragment() {
    private val viewModel: TimeLineViewModel by viewModels()
    private val adapter = TimeLineAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            viewModel.getTimeLines(wd).collectLatest {
                adapter.submitData(it)
            }
        }
        return binding.root
    }

    fun initView() {
    }
}