package com.jepack.banner.example;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jepack.banner.BannerView;
import com.jepack.banner.ImageLoader;
import com.jepack.banner.example.R;
import com.jepack.banner.model.BannerItem;

import java.net.URI;

/**
 * 用于存放Banner
 * Created by zhanghaihai on 2017/12/18.
 */

public class BannerFragment extends Fragment{
    private BannerView bannerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_banner, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bannerView = view.findViewById(R.id.banner_view);
        bannerView.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(String imageUrl, ImageView imageView) {
                Glide.with(getContext()).load(imageUrl).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
            }
        });

        Button refresh = view.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bannerView.addItems(
                        new BannerItem("title1", "subtitle1", "http://soft.wmkankan.com/image/aa7290f5a95d29bf30f6e804201fe864.jpg", null, null),
                        new BannerItem("title2", "subtitle2", "http://bpic.588ku.com/element_banner/20/17/12/6081fcb8bedcd52abfd310f5bc586107.jpg", null, null),
                        new BannerItem("title3", "subtitle3", "http://bpic.588ku.com/element_banner/20/17/12/431fc7e79a295281ff632836897cefb3.jpg", null, null),
                        new BannerItem("title4", "subtitle4", "http://bpic.588ku.com/element_banner/20/17/12/7ded54a0c52c06641ee9aa94a99421df.jpg", null, null),
                        new BannerItem("title5", "subtitle5", "http://bpic.588ku.com/element_banner/20/17/12/b5a0946aa6b7ac000f377e77d5726fe7.jpg", null, null),
                        new BannerItem("title6", "subtitle6", "http://bpic.588ku.com/element_banner/20/17/12/147b4d99704cc31c63e0fd65e0756be6.jpg", null, null),
                        new BannerItem("title7", "subtitle7", "http://bpic.588ku.com/element_banner/20/17/12/4072c394486390ef136b93007cd52ab7.jpg", null, null),
                        new BannerItem("title8", "subtitle8", "http://bpic.588ku.com/element_banner/20/17/12/d18135657d291326458d18ffd8e2797d.jpg", null, null),
                        new BannerItem("title9", "subtitle9", "http://bpic.588ku.com/element_banner/20/17/12/ec2346e09d713aab5199a045cbd9919b.jpg", null, null)
                );
            }
        });

        bannerView.startAutoPlay();
    }

    @Override
    public void onDestroyView() {
        bannerView.startAutoPlay();
        super.onDestroyView();
    }
}
