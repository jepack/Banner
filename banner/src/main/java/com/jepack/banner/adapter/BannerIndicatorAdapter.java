package com.jepack.banner.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jepack.banner.BannerUtil;
import com.jepack.banner.R;
import com.jepack.banner.databinding.BannerIndicatorBinding;
import com.jepack.banner.model.Banner;

/**
 *
 * Created by zhanghaihai on 2017/12/18.
 */

public class BannerIndicatorAdapter extends RecyclerView.Adapter<BannerIndicatorAdapter.IndicatorHolder> {
    private Banner banner;
    public BannerIndicatorAdapter(@NonNull Banner banner) {
        this.banner = banner;
    }

    @Override
    public BannerIndicatorAdapter.IndicatorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BannerIndicatorBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.widget_banner_indicator_item, parent, false);
        return new IndicatorHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(BannerIndicatorAdapter.IndicatorHolder holder, int position) {
        holder.binding.setItem(banner.items.get().get(position));
    }

    @Override
    public int getItemCount() {
        return BannerUtil.getRealCount(banner);
    }

    public static class IndicatorHolder extends RecyclerView.ViewHolder{
        private BannerIndicatorBinding binding;
        private IndicatorHolder(View itemView, BannerIndicatorBinding binding) {
            super(itemView);
            this.binding = binding;
        }
        private IndicatorHolder(View itemView) {
            super(itemView);
        }
    }
}
