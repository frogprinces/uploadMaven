package com.example.zhaoshuai.mydemocollection.material;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhaoshuai.mydemocollection.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.List;

/**
 * Created by zs on 2017/8/31.
 *
 * Material Design设计风格的页面
 */

public class DetailActivity2 extends AppCompatActivity {

    private ListView mListView;
    private List<String> mList;

    private int mOffset = 0;
    private int mScrollY = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        SmartRefreshLayout refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        final TextView textView = (TextView) findViewById(R.id.tv_title);
        setSupportActionBar(toolbar);

        //状态栏透明和间距处理
        //StatusBarUtil.immersive(this);


        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setTitle("一丝丝纯真");


        toolbar.setBackgroundColor(0);
        textView.setAlpha(0);

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset,
                    int footerHeight, int extendHeight) {
                mOffset = offset / 2;
                toolbar.setAlpha(1 - Math.min(percent, 1));
            }

            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset,
                    int headerHeight, int extendHeight) {
                mOffset = offset / 2;
                toolbar.setAlpha(1 - Math.min(percent, 1));
            }
        });


        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(170);
            private int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)&0x00ffffff;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX,
                    int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    textView.setAlpha(1f * mScrollY / h);
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                }
                lastScrollY = scrollY;
            }
        });
    }
}
