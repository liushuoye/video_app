package com.shuoye.video.ui.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuoye.video.databinding.FragmentRecyclerViewBinding
import com.shuoye.video.ui.update.adapter.RecommendPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * TODO
 * @program Video
 * @ClassName RecommendFragment
 * @author shuoye
 * @create 2021-11-04 13:31
 **/
@AndroidEntryPoint
class RecommendFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerViewBinding
    private val model: RecommendViewModel by viewModels()
    private val adapter = RecommendPagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        context ?: return binding.root
        init()
        return binding.root
    }

    private fun init() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        lifecycleScope.launch {
            model.getRecommend().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}