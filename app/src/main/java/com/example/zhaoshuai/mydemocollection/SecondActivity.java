package com.example.zhaoshuai.mydemocollection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private ListView mListView;
    private SmartRefreshLayout mRefreshLayout;
    private List<String> mList;
    private ImageView mIvHeaderBackground;

    private int mOffset = 0;
    private int mScrollY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_rerfesh);
        TextView textView = (TextView) findViewById(R.id.tv_string);

        //状态栏透明和间距处理
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, textView);


        //initView();
        //initData();
    }

    /**
     * 初始化View
     */
    private void initView() {
        //mListView = (ListView) findViewById(R.id.list_view);
        //mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        mIvHeaderBackground = (ImageView) findViewById(R.id.iv_header_background);

        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset,
                    int headerHeight, int extendHeight) {
                mOffset = offset / 2;
                mIvHeaderBackground.setTranslationY(mOffset - mScrollY);

            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset,
                    int footerHeight, int extendHeight) {
                mOffset = offset / 2;
                mIvHeaderBackground.setTranslationY(mOffset - mScrollY);
            }
        });


        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Toast.makeText(SecondActivity.this,"下拉刷新成功", Toast.LENGTH_SHORT).show();
                pullDone();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Toast.makeText(SecondActivity.this,"上拉加载成功", Toast.LENGTH_SHORT).show();
                pullDone();
            }
        });

    }

    /**
     * 结束加载动作
     */
    private void pullDone(){
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mList = new ArrayList<>();
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("张三");
        mList.add("李四");


        mListView.setAdapter(new MyAdapter());
    }

     private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                view = LayoutInflater.from(SecondActivity.this).inflate(R.layout.item_food,viewGroup ,false);
            }

            TextView userName = (TextView) view.findViewById(R.id.tv_user_name);
            userName.setText(mList.get(i));

            return view;
        }
    }
}
