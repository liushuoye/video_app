package com.shuoye.video.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * TODO
 * @program Video
 * @ClassName ViewHolder
 * @author shuoye
 * @create 2021-10-23 23:48
 **/


class ViewHolder<T : ViewDataBinding>(
    binding: T,
) : RecyclerView.ViewHolder(binding.root)