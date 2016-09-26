package com.qiyi.guianxiang.testlistview;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by guianxiang on 16-9-23.
 */

public class myAdapter extends BaseAdapter {
    private Context mContext;
    private View commentLayer;
    private CustomListView mlistView;
    private int[] datas;
    private int clickbottom = 0;
    private int offset = 0;
    private int clickPosition;
    private int clickItemHeight;
    private int newH = 0;

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int i) {
        return datas[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public myAdapter(Context context, CustomListView listView, int[] datas) {
        mContext = context;
        mlistView = listView;
        this.datas = datas;
        initInputBar();

        mlistView.setmListener(new OnResizeListener() {
            @Override
            public void OnResize(int w, int h, int oldw, int oldh) {
                Log.e("test", "listOnResize: " + w + "," + h + "," + oldw + "," + oldh);
                //键盘弹出
                if (oldh > h) {
                    //(clicked item's bottom to the listview's bottom after keyboard appear) - (clicked item's bottom to the listview's bottom before keyboard appear)
                    offset = (oldh - h + commentLayer.getHeight()) - (oldh - clickbottom);
                    newH = h;
                    mlistView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mlistView.scrollListBy(offset);
                            //following is another way to realize but require a higher API version
//                            mlistView.setSelectionFromTop(clickPosition, newH - commentLayer.getHeight() - clickItemHeight);
                        }
                    }, 20);
                }
            }
        });
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        final int position = i;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, viewGroup, false);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.reply = (TextView) view.findViewById(R.id.reply);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText("android:" + datas[i]);

        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputBar();
                //attention: getChildAt()is to get the visible item in ListView area
                clickbottom = mlistView.getChildAt(position - mlistView.getFirstVisiblePosition()).getBottom();
                clickItemHeight = mlistView.getChildAt(position - mlistView.getFirstVisiblePosition()).getHeight();
                clickPosition = position;
            }
        });
        return view;
    }

    public static class ViewHolder {
        public ImageView icon;
        public TextView name;
        public TextView reply;
    }

    private void showInputBar() {
        commentLayer.setVisibility(View.VISIBLE);
        EditText comment_et = (EditText) commentLayer.findViewById(R.id.comment_et);
        keyboardUtils.openSoftKeyboard(comment_et);
    }

    private void initInputBar() {
        commentLayer = ((Activity) (mContext)).findViewById(R.id.input_layer);
        commentLayer.setVisibility(View.GONE);
    }
}
