package com.shuoye.video.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.shuoye.video.adapters.ViewHolder
import com.shuoye.video.database.pojo.TimeLine
import com.shuoye.video.databinding.ItemTimeLineBinding

/**
 * TODO
 * @program Video
 * @ClassName TimeLineAdapter
 * @author shuoye
 * @create 2021-10-24 15:45
 **/
class TimeLineAdapter() :
    PagingDataAdapter<TimeLine, ViewHolder<ItemTimeLineBinding>>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ItemTimeLineBinding> {
        return ViewHolder(
            ItemTimeLineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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