package com.jepack.banner.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jepack.banner.BannerUtil;
import com.jepack.banner.R;
import com.jepack.banner.databinding.BannerImageBinding;
import com.jepack.banner.model.Banner;
import com.jepack.banner.model.BannerItem;

/**
 *
 * Created by zhanghaihai on 2017/12/18.
 */

public class BannerImageAdapter extends RecyclerView.Adapter<BannerImageAdapter.ImageHolder> {
    private Banner banner;

    public BannerImageAdapter(@NonNull Banner banner) {
        this.banner = banner;
    }

    @Override
    public BannerImageAdapter.ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BannerImageBinding bannerImageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.widget_banner_img_item, parent, false);
        return new ImageHolder(bannerImageBinding.getRoot(), bannerImageBinding);
    }

    @Override
    public void onBindViewHolder(BannerImageAdapter.ImageHolder holder, int position) {
        if(holder != null && banner != null && banner.items.get() != null && banner.items.get().size() > 0) {
            BannerItem item = banner.items.get().get(BannerUtil.getRealPosition(banner, position));
            holder.binding.setItem(item);
        }

    }

    @Override
    public int getItemCount() {
        return BannerUtil.getEndlessItemCount(banner);
    }

    public static class ImageHolder extends RecyclerView.ViewHolder{
        private BannerImageBinding binding;
        private ImageHolder(View itemView, BannerImageBinding binding) {
            super(itemView);
            this.binding = binding;
        }
        private ImageHolder(View itemView) {
            super(itemView);
        }
    }

}
