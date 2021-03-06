package com.example.zhaoshuai.mydemocollection.scroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * Created by zs on 2017/9/13.
 *
 *
 */

public class TimeDepositFooter extends LinearLayout implements RefreshFooter {

    private TextView mTitle;

    public TimeDepositFooter(Context context) {
        super(context);
        this.initView(context);
    }

    public TimeDepositFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public TimeDepositFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context){
        setGravity(Gravity.CENTER);
        mTitle = new TextView(context);
        mTitle.setText("上拉查看产品详情");
        addView(mTitle);
    }



    @Override
    public void onPullingUp(float percent, int offset, int footerHeight, int extendHeight) {

    }

    @Override
    public void onPullReleasing(float percent, int offset, int footerHeight, int extendHeight) {

    }

    @Override
    public boolean setLoadmoreFinished(boolean finished) {
        return true;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        return 0;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState,
            RefreshState newState) {
        switch (newState) {
        case None:
            Log.d("TimeDepositFooter", "None--");
        case PullToUpLoad:
            Log.d("TimeDepositFooter", "PullToUpLoad--");
            mTitle.setText("上拉查看产品详情");
            break;
        case Loading:
            Log.d("TimeDepositFooter", "Loading--");
            break;
        case ReleaseToLoad:
            Log.d("TimeDepositFooter", "ReleaseToLoad--");
            mTitle.setText("松手进入产品详情");
            break;
        case Refreshing:
            Log.d("TimeDepositFooter", "Refreshing--");
            break;
        }
    }


}
