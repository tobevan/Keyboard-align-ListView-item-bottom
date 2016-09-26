package com.qiyi.guianxiang.testlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by guianxiang on 16-9-26.
 */

public class CustumListView extends ListView {
    private static final String TAG = "CustumListView";

    public void setmListener(OnResizeListener mListener) {
        this.mListener = mListener;
    }

    private OnResizeListener mListener;



    public CustumListView(Context context) {
        super(context);
    }

    public CustumListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustumListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mListener.OnResize(w, h, oldw, oldh);

    }
}
