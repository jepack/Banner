package com.jepack.banner;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jepack.banner.databinding.BannerImageBinding;
import com.jepack.banner.model.Banner;

/**
 * Created by zhanghaihai on 2017/12/20.
 */

public class BannerUtil {
    public static int getEndlessItemCount(Banner banner){
        return getRealCount(banner) == 0? 0: Integer.MAX_VALUE;
    }

    public static int getRealCount(Banner banner){
        return (banner == null || banner.items.get() == null ||
                banner.items.get().size() == 0 )? 0: banner.items.get().size();
    }


    /**
     * 获取循环滚动的初始位置
     * @param banner
     * @param offset
     * @return
     */
    public static int getEndlessInitializePosition(Banner banner, int offset){
        if((banner == null || banner.items.get() == null ||
                banner.items.get().size() == 0 )) {
            return 0;
        }else{
            int halfMax = Integer.MAX_VALUE / 2;
            return halfMax - halfMax % banner.items.get().size() + offset;
        }
    }

    /**
     * 获取真实的Position
     * @param banner
     * @param dummyPos
     * @return
     */
    public static int getRealPosition(Banner banner, int dummyPos){
        if(banner != null && banner.items.get() != null && banner.items.get().size() > 0) {
            return dummyPos % banner.items.get().size();
        }else{
            return 0;
        }
    }

}
