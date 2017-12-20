package com.jepack.banner;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.jepack.banner.adapter.BannerImageAdapter;
import com.jepack.banner.adapter.BannerIndicatorAdapter;
import com.jepack.banner.databinding.BannerViewBinding;
import com.jepack.banner.model.Banner;
import com.jepack.banner.model.BannerItem;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自定义Banner，基于DataBinding (MVVM)
 * Created by zhanghaihai on 2017/12/18.
 */

public class BannerView extends FrameLayout{

    private final Banner banner = new Banner();
    private RecyclerView imageListView;
    private RecyclerView indicator;
    private BannerImageAdapter bannerImageAdapter;
    private @LayoutRes int bannerLayout = R.layout.widget_banner;
    private PagerSnapHelper snapHelper;
    private AtomicBoolean isScrolling = new AtomicBoolean(false);
    private PagingScheduler pagingScheduler = new PagingScheduler(banner);

    public BannerView(Context context) {
        super(context);
        initViews();

    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        BannerViewBinding bannerViewBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getBannerLayout(), null, false);
        View view = bannerViewBinding.getRoot();
        banner.titleTextSize.set(getContext().getResources().getDimension(R.dimen.indicator_title_txt_size));
        banner.subtitleTextSize.set(getContext().getResources().getDimension(R.dimen.indicator_subtitle_txt_size));
        bannerViewBinding.setBanner(banner);
        bannerViewBinding.executePendingBindings();
        imageListView = view.findViewById(R.id.banner_img_list);
        indicator = view.findViewById(R.id.banner_indicator);
        addView(view); //将自定义的布局添加到BannerView

        //横向滚动
        LinearLayoutManager imgLinearLayoutManager = new LinearLayoutManager(getContext());
        imgLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager indicatorLinearLayoutManager = new LinearLayoutManager(getContext());
        indicatorLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        indicator.addItemDecoration(new BannerIndicatorDecoration(0, 10, 10, 0, Color.TRANSPARENT, Color.TRANSPARENT));
        indicator.setLayoutManager(indicatorLinearLayoutManager);
        imageListView.setLayoutManager(imgLinearLayoutManager);

        //单页滑动
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(imageListView);

        indicator.setAdapter(new BannerIndicatorAdapter(banner));
        bannerImageAdapter = new BannerImageAdapter(banner);
        imageListView.setAdapter(new BannerImageAdapter(banner));
        imageListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                changeSelected(snapHelper);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(pagingScheduler == null ) return;
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    pagingScheduler.startAutoTurningPage();
                }else{
                    pagingScheduler.stopAutoTurningPage();
                }
            }
        });
    }

    /**
     * 更改选中Item信息
     */
    private void changeSelected(SnapHelper snapHelper){
        View view = snapHelper.findSnapView(imageListView.getLayoutManager());
        if(view != null && bannerImageAdapter != null) {
            int position = imageListView.getLayoutManager().getPosition(view);
            int realPosition = BannerUtil.getRealPosition(banner, position);
            if(realPosition >= 0 && realPosition < banner.items.get().size()) {
                banner.setSelected(realPosition, position);
            }
        }
    }

    /**
     * 更改选中Item信息, 若正在滚动此操作不生效
     */
    private void changeSelectedIfNotScrolling(){
        if(!isScrolling.get()){
            changeSelected(snapHelper);
        }
    }

    /**
     * 添加Item
     * @param items
     */
    public void addItems(BannerItem ...items){

        for(BannerItem bannerItem: items) {
            bannerItem.setImageLoader(banner.imageLoader);
            banner.items.get().add(bannerItem);
        }
        //首次添加item需要初始化位置
        if(items.length == banner.items.get().size()) {
            imageListView.scrollToPosition(BannerUtil.getEndlessInitializePosition(banner, 0));
        }
        imageListView.getAdapter().notifyDataSetChanged();
        indicator.getAdapter().notifyDataSetChanged();
    }

    public void addItems(List<BannerItem> itemList){
        final int length = itemList.size();
        BannerItem[] items = new BannerItem[length];
        addItems(itemList.toArray(items));
    }

    public int getBannerLayout() {
        return bannerLayout;
    }

    /**
     * 你可以自定义Banner布局
     * 具体包含的View及ID请参考 {@link @layout/widget_banner}
     * @param bannerLayout
     */
    public void setBannerLayout(int bannerLayout) {
        this.bannerLayout = bannerLayout;
    }

    public void setImageLoader(ImageLoader imageLoader){
        banner.setImageLoader(imageLoader);
    }

    public void startAutoPlay(){
        pagingScheduler.startAutoTurningPage();
    }

    public void stopAutoPlay(){
        pagingScheduler.stopAutoTurningPage();
    }
}

