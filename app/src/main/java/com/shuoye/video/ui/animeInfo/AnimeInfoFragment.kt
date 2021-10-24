package com.shuoye.video.ui.animeInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.shuoye.video.databinding.FragmentAnimeInfoBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * TODO
 * @program Video
 * @ClassName AnimeInfoFragment
 * @author shuoye
 * @create 2021-10-24 21:55
 **/
@AndroidEntryPoint
class AnimeInfoFragment : Fragment() {
    private val viewModel: AnimeInfoViewModel by viewModels()
    private val args: AnimeInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAnimeInfoBinding.inflate(inflater, container, false)
        context ?: return binding.root
        viewModel.getAnime(args.animeInfoId).observe(viewLifecycleOwner, {
            binding.anime = it.data
        })
        return binding.root
    }
}