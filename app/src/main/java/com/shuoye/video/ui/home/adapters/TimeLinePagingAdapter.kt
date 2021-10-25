package com.shuoye.video.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.shuoye.video.adapters.ViewHolder
import com.shuoye.video.database.pojo.TimeLine
import com.shuoye.video.databinding.ItemTimeLineBinding
import com.shuoye.video.ui.home.HomeFragmentDirections

/**
 * TODO
 * @program Video
 * @ClassName TimeLinePagingAdapter
 * @author shuoye
 * @create 2021-10-24 15:45
 **/
class TimeLinePagingAdapter() :
    PagingDataAdapter<TimeLine, ViewHolder<ItemTimeLineBinding>>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ItemTimeLineBinding> {
        val binding = ItemTimeLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.root.setOnClickListener { view: View ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToAnimeInfoFragment(
                    binding.timeLine?.id ?: 20180213
                )
            view.findNavController().navigate(action)
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemTimeLineBinding>, position: Int) {
        val timeLine = getItem(position)
        holder.binding.timeLine = timeLine
    }

}

private class DiffCallback : DiffUtil.ItemCallback<TimeLine>() {


    override fun areItemsTheSame(oldItem: TimeLine, newItem: TimeLine): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TimeLine, newItem: TimeLine): Boolean {
        return oldItem == newItem
    }
}