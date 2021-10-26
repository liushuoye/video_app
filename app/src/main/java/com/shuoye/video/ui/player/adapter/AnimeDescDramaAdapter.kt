package com.shuoye.video.ui.player.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.button.MaterialButton
import com.shuoye.video.R
import com.shuoye.video.ui.player.bean.AnimeDescDetailsBean

/**
 * 展开播放列表适配器
 */
open class AnimeDescDramaAdapter(val mContext: Context, data: List<AnimeDescDetailsBean?>?) :
    BaseQuickAdapter<AnimeDescDetailsBean?, BaseViewHolder>(
        R.layout.item_desc_drama,
        data as MutableList<AnimeDescDetailsBean?>?
    ) {
    override fun convert(holder: BaseViewHolder, item: AnimeDescDetailsBean?) {
        val materialButton: MaterialButton = holder.getView(R.id.tag_group)
        holder.setText(R.id.tag_group, item?.title)
        if (item?.selected == true) {
            materialButton.setTextColor(context.resources.getColor(R.color.tabSelectedTextColor))
        } else {
            materialButton.setTextColor(
                context.resources.getColor(R.color.text_color_primary)
            )
        }
    }

}