package com.shuoye.video.ui.animeInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.shuoye.video.adapters.ViewHolder
import com.shuoye.video.database.pojo.Anime
import com.shuoye.video.databinding.ItemSeriesBinding
import com.shuoye.video.ui.animeInfo.AnimeInfoFragmentDirections
import com.shuoye.video.utils.VideoUtils

/**
 * 动漫系列适配器
 * @program Video
 * @ClassName AnimeSeriesAdapter
 * @author shuoye
 * @create 2021-11-16 15:47
 **/
class AnimeSeriesAdapter(
    private val anime: Anime
) : RecyclerView.Adapter<ViewHolder<ItemSeriesBinding>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ItemSeriesBinding> {
        val binding = ItemSeriesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.root.setOnClickListener { view ->
            val action = AnimeInfoFragmentDirections.actionAnimeInfoFragmentSelf(
                binding.animeInfo?.id ?: 20180213
            )
            view.findNavController().navigate(action)
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemSeriesBinding>, position: Int) {
        val animeInfo = anime.series[position]
        holder.binding.animeInfo = animeInfo
        Glide.with(holder.itemView)
            .load(
                VideoUtils.getSiteUrl(animeInfo.cover)
            )
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(holder.binding.cover)
    }

    override fun getItemCount(): Int {
        return anime.series.size
    }
}