package com.shuoye.video.ui.update.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.shuoye.video.adapters.ViewHolder
import com.shuoye.video.database.pojo.Recommend
import com.shuoye.video.databinding.ItemRecommendBinding
import com.shuoye.video.ui.update.RecommendFragmentDirections
import com.shuoye.video.utils.VideoUtils

/**
 * TODO
 * @program Video
 * @ClassName RecommendPagingAdapter
 * @author shuoye
 * @create 2021-11-04 14:24
 **/
class RecommendPagingAdapter : PagingDataAdapter<Recommend, ViewHolder<ItemRecommendBinding>>(
    object : DiffUtil.ItemCallback<Recommend>() {
        override fun areItemsTheSame(oldItem: Recommend, newItem: Recommend): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recommend, newItem: Recommend): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onBindViewHolder(holder: ViewHolder<ItemRecommendBinding>, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.binding.recommend = item
            Glide.with(holder.itemView)
                .load(VideoUtils.getSiteUrl(item.picSmall))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(holder.binding.imageView2)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ItemRecommendBinding> {
        val binding = ItemRecommendBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.root.setOnClickListener { view ->
            val action = RecommendFragmentDirections.actionRecommendFragmentToAnimeInfoFragment(
                binding.recommend?.id ?: 20180213
            )
            view.findNavController().navigate(action)
        }
        return ViewHolder(binding)
    }

}