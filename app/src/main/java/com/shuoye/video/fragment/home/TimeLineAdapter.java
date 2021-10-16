package com.shuoye.video.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.shuoye.video.R;
import com.shuoye.video.adapter.ViewHolder;
import com.shuoye.video.databinding.ItemTimeLineBinding;
import com.shuoye.video.pojo.TimeLine;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 新番时间表适配器
 *
 * @author shuoy
 */
public class TimeLineAdapter extends RecyclerView.Adapter<ViewHolder<ItemTimeLineBinding>> {
    private final List<TimeLine> data;

    public TimeLineAdapter(List<TimeLine> data) {
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder<ItemTimeLineBinding> onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_time_line, parent, false);
        ViewHolder<ItemTimeLineBinding> holder = new ViewHolder<>(view);
        holder.binding = ItemTimeLineBinding.bind(holder.itemView);
        holder.binding.getRoot().setOnClickListener(v -> {
            String id = (String) holder.itemView.getTag();
            Log.d("Shuoye", id);
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            NavController navController = Navigation.findNavController(view);//获取导航控制器
//            navController.navigate(R.id.action_homeFragment_to_animeInfoFragment, bundle);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder<ItemTimeLineBinding> holder, int position) {
        if (data != null) {
            TimeLine item = data.get(position);
            holder.itemView.setTag(item.getId());
            holder.binding.name.setText(item.getName());
            holder.binding.tag.setText(item.getEpisodes());
            Glide.with(holder.itemView)
                    .load(item.getCover())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .into(holder.binding.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
