package com.example.zhaoshuai.mydemocollection.alpha;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by zs on 2017/9/4.
 *
 * 监测ScrollView的滑动距离
 */

public class MonitorScrollView extends ScrollView {

    private OnScrollChangedListener onScrollChangedListener = null;

    public MonitorScrollView(Context context) {
        super(context);
    }

    public MonitorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonitorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(this, x, y, oldX, oldY);
        }
    }

    public interface OnScrollChangedListener {

        void onScrollChanged(MonitorScrollView scrollView, int x, int y, int oldX, int oldY);

    }

}
