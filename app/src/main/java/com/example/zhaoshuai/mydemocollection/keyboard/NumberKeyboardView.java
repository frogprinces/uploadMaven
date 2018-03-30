package com.example.zhaoshuai.mydemocollection.keyboard;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhaoshuai.mydemocollection.R;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zs on 2017/9/7.
 *
 * 自定义数字键盘
 */

public class NumberKeyboardView extends RelativeLayout{

    /** 页面控件: 小数点 */
    private TextView mPoint;

    /** 键盘事件监听 */
    private IOnKeyboardListener mOnKeyboardListener;
    private IOnFunctionListener mOnFunctionListener;

    /** 输入框输入信息 */
    private StringBuilder mStringBuilder = new StringBuilder();
    private EditText mEditText;

    /** 上下文 */
    private Activity mActivity;

    public NumberKeyboardView(Context context) {
        super(context);
        init(context);
    }

    public NumberKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumberKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    private void init(Context context) {
        View view = View.inflate(context, R.layout.view_number_keyboard, null);

        ButterKnife.bind(this, view);
        mPoint = (TextView) view.findViewById(R.id.tv_point);

        addView(view);
        hideDecimalPointKey();
    }

    /**
     * 绑定EditText
     *
     * @param activity 上下文
     * @param editText 需要绑定的EditText
     */
    public void bindEditText(Activity activity, EditText editText){
        this.mEditText = editText;
        this.mActivity = activity;

        forbidSystemKeyboard();
    }

    @OnClick({R.id.ll_delete, R.id.tv_finish, R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_four,
            R.id.tv_five, R.id.tv_six, R.id.tv_seven, R.id.tv_eight, R.id.tv_nine, R.id.tv_point,
            R.id.tv_zero, R.id.ll_hide_keyboard})
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.tv_one:
            insertNumber("1");
            break;
        case R.id.tv_two:
            insertNumber("2");
            break;
        case R.id.tv_three:
            insertNumber("3");
            break;
        case R.id.tv_four:
            insertNumber("4");
            break;
        case R.id.tv_five:
            insertNumber("5");
            break;
        case R.id.tv_six:
            insertNumber("6");
            break;
        case R.id.tv_seven:
            insertNumber("7");
            break;
        case R.id.tv_eight:
            insertNumber("8");
            break;
        case R.id.tv_nine:
            insertNumber("9");
            break;
        case R.id.tv_zero:
            insertNumber("0");
            break;
        case R.id.tv_point:
            insertNumber(".");
            break;
        case R.id.ll_delete:
            deleteNumber();
            break;
        case R.id.tv_finish:
            if(mOnFunctionListener != null){
                mOnFunctionListener.onFinishKeyEvent();
            }
            break;
        case R.id.ll_hide_keyboard:
            if(mOnFunctionListener != null){
                mOnFunctionListener.onHideKeyEvent();
            }
            break;
        default:
            break;
        }
    }

    /**
     * 输入数值
     *
     * @param value 数值
     */
    private void insertNumber(String value) {

        if(mEditText == null){
            return;
        }

        int index = mEditText.getSelectionStart();

        //兼容光标移动内容的插入问题
        if (index == mStringBuilder.toString().length()) {
            mStringBuilder.append(value);
            mEditText.setText(mStringBuilder.toString());
            Editable editable = mEditText.getText();
            mEditText.setSelection(editable.length());
        } else {
            Editable editable = mEditText.getText();
            editable.insert(index, value);
            mStringBuilder.append(value, 0, 1);
        }

        if (mOnKeyboardListener != null) {
            mOnKeyboardListener.onInsertKeyEvent(mStringBuilder.toString());
        }
    }

    /**
     * 删除数值
     */
    private void deleteNumber() {

        if(mEditText == null){
            return;
        }

        String amount = mEditText.getText().toString().trim();

        //兼容光标移动内容的删除问题
        if (amount.length() > 0) {
            int index = mEditText.getSelectionStart();
            if (index == amount.length()) {
                deleteValue();
                amount = amount.substring(0, amount.length() - 1);
                mEditText.setText(amount);
                Editable ea = mEditText.getText();
                mEditText.setSelection(ea.length());
            } else {
                if(index != 0){
                    deleteValue(index-1,index);
                    Editable editable = mEditText.getText();
                    editable.delete(index-1, index);
                }
            }
        }

        if(mOnKeyboardListener != null){
            mOnKeyboardListener.onDeleteKeyEvent();
        }
    }

    /**
     * 显示小数点
     */
    public void showDecimalPointKey(){
        mPoint.setText(".");
        mPoint.setClickable(true);
    }

    /**
     * 隐藏小数点
     */
    public void hideDecimalPointKey(){
        mPoint.setText("");
        mPoint.setClickable(false);
    }

    /**
     * 获取输入框输入内容
     *
     * @return 输入框输入内容
     */
    public String getInputValue(){
       return mStringBuilder.toString();
    }

    /**
     * 删除输入框内容，从末尾逐个删除
     */
    private void deleteValue(){
        if(mStringBuilder.toString().length() > 0){
            mStringBuilder.delete(mStringBuilder.toString().length() - 1,mStringBuilder.toString().length());
        }
    }

    /**
     * 删除输入框内容，从光标位置删除
     *
     * @param start 启始位置
     * @param end 结束位置
     */
    private void deleteValue(int start, int end){
        if(mStringBuilder.toString().length() > 0){
            mStringBuilder.delete(start, end);
        }
    }

    /**
     * 禁止系统键盘弹出
     */
    private void forbidSystemKeyboard() {
        if(mActivity == null){
            return;
        }
        int sdkVersion = Build.VERSION.SDK_INT;
        if(sdkVersion < 11){
            return;
        }

        this.mActivity.getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(mEditText, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置键盘的监听事件。
     *
     * @param listener 监听事件
     */
    public void setOnKeyboardListener(IOnKeyboardListener listener) {
        this.mOnKeyboardListener = listener;
    }

    /**
     * 设置功能按键的监听
     *
     * @param listener 监听事件
     */
    public void setOnFunctionListener(IOnFunctionListener listener) {
        this.mOnFunctionListener = listener;
    }

    /**
     * 键盘的监听事件。
     */
    public interface IOnKeyboardListener {

        /**
         * 点击数字按键。
         *
         * @param text 输入的数字
         */
        void onInsertKeyEvent(String text);

        /**
         * 点击删除按键。
         */
        void onDeleteKeyEvent();

    }

    /**
     * 键盘功能操作区
     */
    public interface IOnFunctionListener {

        /**
         * 点击确定按键
         */
        void onFinishKeyEvent();

        /**
         * 点击隐藏收起按键
         */
        void onHideKeyEvent();
    }
}
