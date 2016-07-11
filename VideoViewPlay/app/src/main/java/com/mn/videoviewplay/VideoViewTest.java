package com.mn.videoviewplay;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by XiaoNan on 2016/7/4.
 */
public class VideoViewTest extends VideoView {
    private int mWidth;
    private int mHeight;

    public VideoViewTest(Context context) {
        super(context);
    }

    public VideoViewTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSize(int iWidth, int iHeight) {
        mWidth = iWidth;
        mHeight = iHeight;
    }


    //重写VideoView的onMeasure方法，让系统测量的宽高不受视频宽高的限制
    //同时能按照设定的宽高来设定VideoVIew的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iWidth = getDefaultSize(mWidth, widthMeasureSpec);
        int iHeight = getDefaultSize(mHeight, heightMeasureSpec);
        setMeasuredDimension(iWidth, iHeight);
    }
}
