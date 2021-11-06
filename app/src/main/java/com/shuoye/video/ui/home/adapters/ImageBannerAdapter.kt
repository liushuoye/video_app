package com.shuoye.video.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.shuoye.video.database.pojo.Banner
import com.shuoye.video.databinding.ItemBannerImageBinding
import com.shuoye.video.utils.VideoUtils
import com.youth.banner.adapter.BannerAdapter

/**
 * 轮播图适配器
 * @program Video
 * @ClassName ImageBannerAdapter
 * @author shuoye
 * @create 2021-10-23 22:20
 **/
class ImageBannerAdapter(banners: MutableList<Banner>?) :
    BannerAdapter<Banner, BannerViewHolder>(banners) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val binding = ItemBannerImageBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        )
        return BannerViewHolder(binding)
    }

    override fun onBindView(holder: BannerViewHolder?, banner: Banner?, position: Int, size: Int) {
        //图片加载自己实现
        if (holder != null && banner != null) {
            holder.itemView.tag = banner.id
            holder.binding.apply {
                Glide.with(holder.itemView)
                    .load(
                        VideoUtils.getSiteUrl(banner.picUrl)
                    )
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                    .into(this.bannerImage)
                this.banner = banner
                executePendingBindings()
            }
        }
    }
}

class BannerViewHolder(
    val binding: ItemBannerImageBinding,
) : RecyclerView.ViewHolder(binding.root) {
}