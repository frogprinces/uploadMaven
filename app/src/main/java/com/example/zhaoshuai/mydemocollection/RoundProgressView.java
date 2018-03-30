package com.example.zhaoshuai.mydemocollection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zs on 2017/6/8.
 *
 * 圆形进度View
 */

public class RoundProgressView extends View{

    private Paint mPaint;

    public RoundProgressView(Context context) {
        this(context, null);
    }

    public RoundProgressView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public RoundProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        setBackgroundResource(R.drawable.shape_circle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*第一步：绘制一个最外层的圆*/

        //获得中心位置
        int center = getWidth() / 2;

        //去锯齿
        mPaint.setAntiAlias(true);

        /*第一步：绘制一个里层的圆*/
//        mPaint.setColor(getResources().getColor(R.color.color_16000000));
//        int r = center - 4;
//        canvas.drawCircle(center,center,r,mPaint);


        //设置颜色
        mPaint.setColor(getResources().getColor(R.color.color_10000000));
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        int radius = (int) (center - 8 / 2);
        canvas.drawCircle(center, center, radius, mPaint);




//        /*第三步：绘制正中间的文本*/

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(28);
        float textWidth = mPaint.measureText("跳过");
        mPaint.setStrokeWidth(0);
        canvas.drawText("跳过", center - textWidth / 2, center + 28 / 2, mPaint);

        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(4);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, -90, 180, false, mPaint);


    }
}
