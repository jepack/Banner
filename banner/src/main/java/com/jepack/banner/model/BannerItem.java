package com.jepack.banner.model;

import android.databinding.ObservableField;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.jepack.banner.ImageLoader;
import com.jepack.banner.ItemAnimation;
import com.jepack.banner.R;

/**
 * Model for banner item.
 * Created by jepack on 2017/12/18.
 */

public class BannerItem {
    private ObservableField<ImageLoader> imageLoader;
    /**
     * 标题
     */
    public ObservableField<String> title;
    /**
     * 子标题
     */
    public ObservableField<String> subtitle;
    /**
     * 图片地址
     */
    public ObservableField<String> imageUrl;
    /**
     * 指示器图标，可为每个item单独指定
     */
    public ObservableField<Integer> indicatorDrawable;
    /**
     * title自定义动画
     */
    public ObservableField<ItemAnimation> titleAnimation;

    /**
     * image自定义动画
     */
    public ObservableField<ItemAnimation> imgAnimation;
    /**
     * subtitle自定义动画
     */
    public ObservableField<ItemAnimation> subTitleAnimation;
    /**
     * item的指示器自定义动画
     */
    public ObservableField<ItemAnimation> indicatorAnimation;
    public ObservableField<ImageView.ScaleType> scaleType;
    public ObservableField<Boolean> isSelected;

    public BannerItem() {
        title = new ObservableField<>("");
        subtitle = new ObservableField<>("");
        imageUrl = new ObservableField<>(null);
        indicatorDrawable = new ObservableField<>(R.drawable.selector_indicator);
        titleAnimation = new ObservableField<>(null);
        imgAnimation = new ObservableField<>(null);
        subTitleAnimation = new ObservableField<>(null);
        indicatorAnimation = new ObservableField<>(null);
        scaleType = new ObservableField<>(ImageView.ScaleType.CENTER_CROP);
        isSelected = new ObservableField<>(null);
    }

    public BannerItem(String title, String subTitle, String imageUrl, ItemAnimation titleAnimation, ItemAnimation imgAnimation) {
        this();
        this.title.set(title);
        this.subtitle.set(subTitle);
        this.imageUrl.set(imageUrl);
        this.titleAnimation.set(titleAnimation);
        this.imgAnimation.set(imgAnimation);
    }

    public ObservableField<ImageLoader> getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(ObservableField<ImageLoader> imageLoader) {
        this.imageLoader = imageLoader;
    }
}
