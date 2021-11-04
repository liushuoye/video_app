package com.shuoye.video.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.shuoye.video.adapters.ViewHolder
import com.shuoye.video.database.pojo.Search
import com.shuoye.video.databinding.ItemSearchBinding
import com.shuoye.video.ui.search.SearchFragmentDirections
import com.shuoye.video.utils.VideoUtils

/**
 * TODO
 * @program Video
 * @ClassName SearchPagingAdapter
 * @author shuoye
 * @create 2021-11-04 21:59
 **/
class SearchPagingAdapter : PagingDataAdapter<Search, ViewHolder<ItemSearchBinding>>(
    object : DiffUtil.ItemCallback<Search>() {
        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem == newItem
        }

    }
) {
    override fun onBindViewHolder(holder: ViewHolder<ItemSearchBinding>, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.binding.search = item
            Glide.with(holder.itemView)
                .load(VideoUtils.getSiteUrl(item.coverSmall))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(holder.binding.imageView)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ItemSearchBinding> {
        val binding = ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.root.setOnClickListener { view ->
            val action = SearchFragmentDirections.actionSearchFragmentToAnimeInfoFragment(
                binding.search?.id ?: 20180213
            )
            view.findNavController().navigate(action)
        }
        return ViewHolder(binding)
    }
}