package com.jepack.banner.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.jepack.banner.ImageLoader;

/**
 * Created by zhanghaihai on 2017/12/18.
 */

public class BannerDataBindingAdapter {

    @BindingAdapter("scaleType")
    public static void setScaleType(ImageView imageView, ImageView.ScaleType scaleType){
        if(scaleType != null){
            imageView.setScaleType(scaleType);
        }
    }

    @BindingAdapter(value = {"imageLoader", "imageUrl"})
    public static void loadImage(ImageView imageView, ImageLoader loader, String imageUrl){
        if(loader != null){
            loader.displayImage(imageUrl, imageView);
        }
    }

    @BindingAdapter("indicatorSelected")
    public static void indicatorSelected(ImageView imageView, Boolean isSelected){
        imageView.setSelected(isSelected == null ? false: isSelected);
    }

    @BindingAdapter("bannerScrollTo")
    public static void scrollTo(RecyclerView recyclerView, Integer pos){
        recyclerView.scrollToPosition(pos);
    }
}
