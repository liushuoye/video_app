package com.shuoye.video.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author shuoy
 */
public class ViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public T binding;
    public Map<String, Object> data;

    public ViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
    }
}
