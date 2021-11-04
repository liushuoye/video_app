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
import com.shuoye.video.database.pojo.Update
import com.shuoye.video.databinding.ItemUpdateBinding
import com.shuoye.video.ui.update.UpdateFragmentDirections
import com.shuoye.video.utils.VideoUtils

/**
 * TODO
 * @program Video
 * @ClassName UpdatePagingAdapter
 * @author shuoye
 * @create 2021-11-04 14:24
 **/
class UpdatePagingAdapter : PagingDataAdapter<Update, ViewHolder<ItemUpdateBinding>>(
    object : DiffUtil.ItemCallback<Update>() {
        override fun areItemsTheSame(oldItem: Update, newItem: Update): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Update, newItem: Update): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onBindViewHolder(holder: ViewHolder<ItemUpdateBinding>, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.binding.updates = item
            Glide.with(holder.itemView)
                .load(VideoUtils.getSiteUrl(item.picSmall))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(holder.binding.imageView2)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ItemUpdateBinding> {
        val binding = ItemUpdateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.root.setOnClickListener { view ->
            val action = UpdateFragmentDirections.actionUpdateFragmentToAnimeInfoFragment(
                binding.updates?.id ?: 20180213
            )
            view.findNavController().navigate(action)
        }
        return ViewHolder(binding)
    }

}