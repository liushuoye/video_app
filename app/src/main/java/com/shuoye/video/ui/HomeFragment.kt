package com.shuoye.video.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuoye.video.adapters.TestAdapters
import com.shuoye.video.databinding.FragmentHometBinding
import com.shuoye.video.viewModels.TestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val adapter = TestAdapters()
    private val viewModel: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHometBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.timeline.layoutManager = LinearLayoutManager(context)
        binding.timeline.adapter = adapter
        lifecycleScope.launch {
            viewModel.getTimeLines().collectLatest {
                Log.d(
                    "liu_shuoye", "onCreateView: ${
                        it.also {
                            it.toString()
                        }
                    }"
                )
                adapter.submitData(it)
            }
        }
//        lifecycleScope.launch {
//
//            adapter.submitData(PagingData.from(emptyList()))
//        }
        return binding.root
    }
}