package com.example.zhaoshuai.mydemocollection.alpha;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhaoshuai.mydemocollection.R;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * Created by zs on 2017/9/4.
 *
 * 测试滑动的操作
 */

public class ScrollAlphaActivity extends AppCompatActivity implements
        MonitorScrollView.OnScrollChangedListener{

    private RelativeLayout mRlTitle;
    private MonitorScrollView mScrollView;
    private TextView mTvTitle;


    private int lastScrollY = 0;
    private int h = DensityUtil.dp2px(170);
    private int mScrollY = 0;

    private float mMaxFloatDistance=250f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_alpha);

        //mRlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        mScrollView = (MonitorScrollView) findViewById(R.id.scroll_view);
        //mTvTitle = (TextView) findViewById(R.id.tv_title);

//        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_F67B52));
        //StatusBarUtil.immersive(this);

        //initListener();

    }

    private void initListener() {
        mScrollView.setOnScrollChangedListener(this);
        mRlTitle.setAlpha(0);
        mTvTitle.setAlpha(0);
    }

    @Override
    public void onScrollChanged(MonitorScrollView scrollView, int scrollX, int scrollY, int oldX, int oldY) {
        float alphaPercent = (float) scrollY / (float) mMaxFloatDistance;
        if (alphaPercent > 1f) {
            //透明百分比大于1修正为1
            alphaPercent = 1f;
        }
        mRlTitle.setAlpha(alphaPercent);
        mTvTitle.setAlpha(alphaPercent);
        mRlTitle.setVisibility(View.VISIBLE);
    }
}
