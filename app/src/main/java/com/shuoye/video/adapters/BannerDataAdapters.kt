package com.shuoye.video.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shuoye.video.database.pojo.Banner
import com.shuoye.video.databinding.ItemTestBinding

/**
 * 适配器
 * @program Video
 * @ClassName BannerDataAdapters
 * @author shuoye
 * @create 2021-10-21 19:50
 **/
class BannerDataAdapters(
    private val banners: ArrayList<Banner>
) : PagingDataAdapter<Banner, BannerDataAdapters.ViewHolder>(TestDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val banner = getItem(position)
        if (banner != null) {
            banners.add(banner)
        }
    }

    class ViewHolder(
        binding: ItemTestBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}


private class TestDiffCallback : DiffUtil.ItemCallback<Banner>() {


    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {

        return oldItem == newItem
    }
}