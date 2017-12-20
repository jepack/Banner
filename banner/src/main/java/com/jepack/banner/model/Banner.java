package com.jepack.banner.model;

import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;

import com.jepack.banner.BannerUtil;
import com.jepack.banner.ImageLoader;
import com.jepack.banner.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Created by zhanghaihai on 2017/12/18.
 */

public class Banner {
    public final ObservableField<List<BannerItem>> items;
    public ObservableField<BannerType> type;
    public ObservableField<String> title;
    public ObservableField<String> subtitle;
    public ObservableField<Integer> titleColor;
    public ObservableField<Integer> subtitleColor;
    public ObservableField<Float> titleTextSize;
    public ObservableField<Float> subtitleTextSize;
    public ObservableField<ImageLoader> imageLoader;
    public ObservableField<Integer> scrollToPos;

    private int lastSelected = -1;
    private int currentPos = 0;
    public static final int POINT = 0;
    public static final int POINT_TITLE = 1;
    public static final int POINT_TITLE_SUBTITLE = 2;

    @IntDef({POINT, POINT_TITLE, POINT_TITLE_SUBTITLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BannerType{}

    public Banner() {
        items = new ObservableField<>(Collections.synchronizedList(new ArrayList<BannerItem>()));
        type = new ObservableField<>();
        title = new ObservableField<>("");
        subtitle = new ObservableField<>("");
        titleColor =  new ObservableField<>(Color.WHITE);
        subtitleColor =  new ObservableField<>(Color.WHITE);
        titleTextSize =  new ObservableField<>(36f);
        subtitleTextSize =  new ObservableField<>(24f);
        imageLoader = new ObservableField<>(null);
        scrollToPos = new ObservableField<>(0);
    }

    public void setSelected(int realPosition, int currentPos){
        if(items.get().size() == 0) return;
        BannerItem item = items.get().get(realPosition);
        title.set(item.title.get());
        subtitle.set(item.subtitle.get());
        if(lastSelected >= 0 && lastSelected != realPosition){
            items.get().get(lastSelected).isSelected.set(false);
            items.get().get(realPosition).isSelected.set(true);
        }else{
            items.get().get(realPosition).isSelected.set(true);
        }
        lastSelected = realPosition;
        this.currentPos = currentPos;
    }

    public int findNextPos(){
        return currentPos + 1 < Integer.MAX_VALUE? currentPos + 1:  Integer.MAX_VALUE;
    }

    public int findPrePos(){
        return currentPos - 1 <= 0? 0: currentPos -1;
    }

    public void setScrollToPos(int pos){
        scrollToPos.set(pos);
        setSelected(BannerUtil.getRealPosition(this, pos), pos);
    }


    public ImageLoader getImageLoader() {
        return imageLoader.get();
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader.set(imageLoader);
    }
}
