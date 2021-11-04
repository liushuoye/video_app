package com.shuoye.video.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuoye.video.databinding.FragmentSearchBinding
import com.shuoye.video.ui.search.adapter.SearchPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * TODO
 * @program Video
 * @ClassName SearchFragment
 * @author shuoye
 * @create 2021-11-04 21:48
 **/
@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val model: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private val adapter = SearchPagingAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        context ?: return binding.root
        init()
        return binding.root
    }

    private fun init() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {

                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                lifecycleScope.launch {
                    model.getSearch(s).collectLatest {
                        adapter.submitData(it)
                    }
                }
                return false
            }
        })
    }
}