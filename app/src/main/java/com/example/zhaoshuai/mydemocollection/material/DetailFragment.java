package com.example.zhaoshuai.mydemocollection.material;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhaoshuai.mydemocollection.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import static com.example.zhaoshuai.mydemocollection.R.id.toolbar;

/**
 * Created by zs on 2017/9/4.
 *
 *
 */

public class DetailFragment extends Fragment {

    private int mScrollY = 0;
    private Toolbar mToolbar;
    private NestedScrollView mNestedScrollView;
    private SmartRefreshLayout mRefreshLayout;
    private TextView mTextView;
    private NestedScrollView mMNestedScrollView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mToolbar = (Toolbar) view.findViewById(toolbar);
        mMNestedScrollView = (NestedScrollView) view.findViewById(R.id.scroll_view);

        mRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);

        mTextView = (TextView) view.findViewById(R.id.tv_title);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //StatusBarUtil.immersive(getActivity());

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mToolbar.setBackgroundColor(0);
        mTextView.setAlpha(0);

        mMNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(170);
            private int color = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimary)&0x00ffffff;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX,
                    int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    mTextView.setAlpha(1f * mScrollY / h);
                    mToolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                }
                lastScrollY = scrollY;
            }
        });
    }
}
