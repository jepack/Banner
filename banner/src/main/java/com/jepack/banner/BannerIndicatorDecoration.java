package com.jepack.banner;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhanghaihai on 2017/6/28.
 */

public class BannerIndicatorDecoration extends RecyclerView.ItemDecoration {
    private int verticalDecoration;
    private int horizonDecoration;
    private Paint paint = new Paint();
    private int linePadding;
    private int color;
    private int backgroundColor;

    public BannerIndicatorDecoration(int lineHeight, int verticalDecoration, int horizonDecoration, int linePadding, int color, int backgroundColor) {
        this.verticalDecoration = verticalDecoration;
        this.horizonDecoration = horizonDecoration;
        this.linePadding = linePadding;
        this.color = color;
        this.backgroundColor = backgroundColor;
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        final int lastPosition = state.getItemCount() - 1;//整个RecyclerView最后一个item的position
        final int current = parent.getChildLayoutPosition(view);//获取当前要进行布局的item的position
        if (current == -1) return;//holder出现异常时，可能为-1
        outRect.set(0, 0, horizonDecoration, verticalDecoration);
        if ( !(layoutManager instanceof GridLayoutManager) && (current == lastPosition )) {//判断是否为第一个或最后一个item
            outRect.set(0, 0, 0, 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if(parent.getLayoutManager() instanceof LinearLayoutManager && !(parent.getLayoutManager() instanceof GridLayoutManager) ){
            if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL) {
                drawHorizontal(c, parent);
            } else {
                drawVertical(c, parent);
            }
        }else{
            drawVertical(c, parent);
            drawHorizontal(c, parent);
        }
    }

    /**
     * 绘制纵向 item 分割线
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent){
        final int left = parent.getPaddingLeft() ;
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight() ;
        final int childSize = parent.getChildCount() ;
        if(childSize < 1) return;
        for(int i = 0 ; i < childSize - 1 ; i ++){
            final View child = parent.getChildAt( i ) ;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin ;
            final int bottom = top + verticalDecoration ;
            if(backgroundColor == 0){
                paint.setColor(Color.TRANSPARENT);
            }else{
                paint.setColor(backgroundColor);
            }
            canvas.drawRect(left + linePadding, top , right - linePadding, bottom, paint);
            paint.setColor(color);
            canvas.drawLine(left + linePadding, top + verticalDecoration / 2f , right - linePadding, top + verticalDecoration / 2f, paint);
        }

    }

    /**
     * 绘制横向 item 分割线
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent){
        final int top = parent.getPaddingTop() ;
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom() ;
        final int childSize = parent.getChildCount() ;
        if(childSize < 1) return;
        for(int i = 0 ; i < childSize - 1; i ++){
            final View child = parent.getChildAt( i ) ;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin ;
            final int right = left + horizonDecoration ;
            if(backgroundColor == 0){
                paint.setColor(Color.TRANSPARENT);
            }else{
                paint.setColor(backgroundColor);
            }
            canvas.drawRect(left, top + linePadding, right, bottom - linePadding, paint);
            paint.setColor(color);
            canvas.drawLine(left + horizonDecoration / 2f, top + linePadding, right - horizonDecoration / 2f, bottom - linePadding, paint);
        }
    }

}
