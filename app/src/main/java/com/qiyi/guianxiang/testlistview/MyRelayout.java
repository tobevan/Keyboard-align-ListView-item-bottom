package com.qiyi.guianxiang.testlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by guianxiang on 16-9-23.
 */

public class MyRelayout extends RelativeLayout {
    private OnResizeListener mListener;

    public MyRelayout(Context context) {
        super(context);
    }

    public MyRelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mListener.OnResize(w, h, oldw, oldh);
    }

    public void setSizeListener(OnResizeListener l) {
        mListener = l;
    }

}
