package com.example.zhaoshuai.mydemocollection.keyboard;

import android.app.Activity;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.zhaoshuai.mydemocollection.R;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * Created by zs on 2017/9/11.
 *
 * 键盘管理
 */

public class NumberKeyboardManager implements NumberKeyboardView.IOnFunctionListener{

    /** 动画时长 */
    private final static int mAnimationDuration = 200;

    /** 页面控件 */
    private ViewGroup mRootView;
    private FrameLayout mKeyboardViewContainer;
    private FrameLayout.LayoutParams mKeyboardViewLayoutParams;
    private  NumberKeyboardView mNumberKeyboardView;

    /** 键盘高度 */
    private int mKeyboardHeight;

    /** 待输入输入框 */
    private EditText mEditText;

    @Override
    public void onFinishKeyEvent() {
        hideKeyboard();
    }

    @Override
    public void onHideKeyEvent() {
        hideKeyboard();
    }

    public NumberKeyboardManager(Activity activity, EditText editText){
        this.mEditText = editText;

        //初始化布局
        mRootView = (ViewGroup) (activity.getWindow().getDecorView().findViewById(android.R.id.content));
        mKeyboardViewContainer = (FrameLayout) LayoutInflater.from(activity).inflate(R.layout.view_manager_keyboard, null);
        mNumberKeyboardView = (NumberKeyboardView) mKeyboardViewContainer.findViewById(R.id.number_keyboard);

        //设置从当前页面的底部弹出
        mKeyboardViewLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mKeyboardViewLayoutParams.gravity = Gravity.BOTTOM;

        //设置键盘操作监听
        mNumberKeyboardView.setOnFunctionListener(this);

        //绑定EditText
        mNumberKeyboardView.bindEditText(activity, editText);

        //初始化EditText的焦点监听事件，失去焦点隐藏键盘
        initFocusListener();
    }

    /**
     * 初始化EditText的焦点监听事件
     */
    private void initFocusListener() {
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard();
                }
            }
        });
    }

    /**
     * 弹出键盘
     */
    public void showKeyboard(){

        //获取键盘高度
        mKeyboardHeight = DensityUtil.dp2px(215);
        mRootView.addView(mKeyboardViewContainer, mKeyboardViewLayoutParams);

        //执行弹出动画
        animateUp();

        //计算是否需要滑动页面
        int moveHeight = getMoveHeight(mEditText);
        if (moveHeight > 0) {
            mRootView.getChildAt(0).scrollBy(0, moveHeight); //移动屏幕
        } else {
            moveHeight = 0;
        }

        //绑定EditText基本位置参数
        mEditText.setTag(R.id.keyboard_view_move_height, moveHeight);
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        //获取滑动距离, 复原屏幕
        int moveHeight = 0;
        Object tag = mEditText.getTag(R.id.keyboard_view_move_height);
        if (null != tag) moveHeight = (int) tag;
        if (moveHeight > 0) {
            mRootView.getChildAt(0).scrollBy(0, -1 * moveHeight);
            mEditText.setTag(R.id.keyboard_view_move_height, 0);
        }

        //执行隐藏动画
        animateDown();

        //将键盘从根布局中移除.
        mRootView.removeView(mKeyboardViewContainer);
    }

    /**
     * 计算屏幕向上移动距离
     *
     * @param view 响应输入焦点的控件
     * @return 移动偏移量
     */
    private int getMoveHeight(View view) {
        //获取当前显示区域的宽高
        Rect rect = new Rect();
        mRootView.getWindowVisibleDisplayFrame(rect);

        //计算输入框在屏幕中的位置
        int[] vLocation = new int[2];
        view.getLocationOnScreen(vLocation);
        int keyboardTop = vLocation[1] + view.getHeight() + view.getPaddingBottom() + view.getPaddingTop();
        //如果输入框到屏幕顶部已经不能放下键盘的高度, 则不需要移动了.
        if (keyboardTop - mKeyboardHeight < 0) {
            return 0;
        }

        //输入框到屏幕的距离 + 键盘高度 如果超出了屏幕的承载范围, 就需要移动.
        int moveHeight = keyboardTop + mKeyboardHeight - rect.bottom;
        return moveHeight > 0 ? moveHeight : 0;
    }

    /**
     * 获取数字键盘
     *
     * @return NumberKeyboardView
     */
    public NumberKeyboardView getNumberKeyboardView(){
        return mNumberKeyboardView;
    }

    /**
     * 弹起键盘动画
     */
    private void animateUp() {
        if (mKeyboardViewContainer == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
        );
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(mAnimationDuration);
        set.setFillAfter(true);
        mKeyboardViewContainer.startAnimation(set);
    }

    /**
     * 隐藏键盘动画
     */
    private void animateDown() {
        if (mKeyboardViewContainer == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
        );
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(mAnimationDuration);
        set.setFillAfter(true);
        mKeyboardViewContainer.startAnimation(set);
    }

}
