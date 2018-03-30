package com.example.zhaoshuai.mydemocollection.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ScrollView;

/**
 * 自定义ScrollView控件。
 * 1. 解决嵌套滚动情况的文字输入问题；
 * 2. 优化键盘定位：EditText下边缘对齐键盘；
 *
 * @author zhangdaisong
 * @since 2016/4/18
 */
public class CustomScrollView extends ScrollView {

    /**
     * @param context
     */
    public CustomScrollView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View findFocus() {
        View view = super.findFocus();

        // 如果焦点控件是EditText，并且父类控件是ScrollView，这时焦点控件返回父控件
        if (view != null && view instanceof EditText) {
            ViewParent parent = view.getParent();
            if (parent != this && parent instanceof ScrollView) {
                view = (View) parent;
            }
        }

        return view;
    }

    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        View currentFocused = super.findFocus();
        if (null == currentFocused || this == currentFocused) {
            return super.computeScrollDeltaToGetChildRectOnScreen(rect);
        }

        // 重新计算需要显示的区域
        Rect tempRect = new Rect();
        currentFocused.getDrawingRect(tempRect);
        offsetDescendantRectToMyCoords(currentFocused, tempRect);

        return super.computeScrollDeltaToGetChildRectOnScreen(tempRect);

    }
}
