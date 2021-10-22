package com.shuoye.video.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shuoye.video.databinding.ItemTestBinding
import com.shuoye.video.database.pojo.TimeLine

const val TAG = "Liu_shuoye"

/**
 * 适配器
 * @program Video
 * @ClassName TestAdapters
 * @author shuoye
 * @create 2021-10-21 19:50
 **/
class TestAdapters : PagingDataAdapter<TimeLine, TestAdapters.ViewHolder>(TestDiffCallback()) {
    init {
        Log.d(TAG, "TestAdapters: ")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        return ViewHolder(
            ItemTestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ")
        val timeLine = getItem(position)
        if (timeLine != null) {
            holder.bind(timeLine)
        }
    }

    class ViewHolder(
        private val binding: ItemTestBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            Log.d(TAG, "ViewHolder: ")
        }

        fun bind(item: TimeLine) {
            Log.d(TAG, "bind: ")
            binding.apply {
                timeLine = item
                // 等待执行绑定
                executePendingBindings()
            }
        }
    }
}


private class TestDiffCallback : DiffUtil.ItemCallback<TimeLine>() {
    init {
        Log.d(TAG, "TestDiffCallback: ")
    }

    override fun areItemsTheSame(oldItem: TimeLine, newItem: TimeLine): Boolean {
        Log.d(TAG, "areItemsTheSame: ")
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TimeLine, newItem: TimeLine): Boolean {
        Log.d(TAG, "areContentsTheSame: ")
        return oldItem == newItem
    }
}