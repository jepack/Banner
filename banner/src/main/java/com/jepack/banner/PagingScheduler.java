package com.jepack.banner;

import android.os.Handler;
import android.os.Message;

import com.jepack.banner.model.Banner;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by zhanghaihai on 2017/12/20.
 */

public class PagingScheduler {
    private Banner banner;
    private AutoTurningPageHandler autoTurningPageHandler = new AutoTurningPageHandler();
    public PagingScheduler(Banner banner){
        this.banner = banner;
    }

    public void turnToNextPage(){
        if(banner != null){
            banner.setScrollToPos(banner.findNextPos());
        }
    }

    public void startAutoTurningPage(){
        autoTurningPageHandler.sendEmptyMessage(0);
    }
    public void stopAutoTurningPage(){
        autoTurningPageHandler.sendEmptyMessage(2);
    }

    private class AutoTurningPageHandler extends Handler{
        private boolean isStopped;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    synchronized (this) {
                        isStopped = false;
                        removeMessages(1);
                        sendEmptyMessageDelayed(1, 8000);
                    }
                    break;
                case 1:
                    synchronized (this) {
                        if (!isStopped) {
                            turnToNextPage();
                            removeMessages(0);
                            sendEmptyMessage(0);
                        }
                    }
                    break;
                case 2:
                    synchronized (this) {
                        isStopped = true;
                    }
                    break;
                case 3:
                    synchronized (this) {
                        isStopped = true;
                        sendEmptyMessage(0);
                    }
                    break;
            }
        }
    }
}
