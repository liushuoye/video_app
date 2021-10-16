package com.shuoye.video.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * @author shuoy
 */
public class ViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public T binding;

    public ViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
    }
}
