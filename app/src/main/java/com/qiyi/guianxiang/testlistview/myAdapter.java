package com.qiyi.guianxiang.testlistview;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by guianxiang on 16-9-23.
 */

public class myAdapter extends BaseAdapter {
    private Context mContext;
    private View commentLayer;
    private CustumListView mlistView;
    private int keyboardHeight;
    private int[] datas;
    private MyRelayout rootView;
    private int cickposition=-1;
    private int clickbottom=0;
    private TextView title;
    private int offset=0;
    private boolean islast=false;

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

    public myAdapter(Context context, CustumListView listView, int[] datas) {
        mContext = context;
        mlistView = listView;
        this.datas = datas;
        initInputBar();
        rootView=(MyRelayout)((Activity)mContext).findViewById(R.id.activity_main);
        title=(TextView) ((Activity)mContext).findViewById(R.id.title);

        mlistView.setmListener(new OnResizeListener() {
            @Override
            public void OnResize(int w, int h, int oldw, int oldh) {
                Log.e("test", "listOnResize: "+h );
                //键盘弹出
                if(oldh>h){
                    mlistView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mlistView.scrollListBy(offset);
                        }
                    },50);
                }
            }
        });

        rootView.setSizeListener(new OnResizeListener() {
            @Override
            public void OnResize(int w, int h, int oldw, int oldh) {

                //键盘弹出
                if(oldh > h && cickposition>=0 ){
                    Log.e("test", "OnResize: "+w+","+h+","+oldw+","+oldh );
                    Log.e("test", "cickposition: "+cickposition );
                    Log.e("test", "commentLayer: "+commentLayer.getHeight() );
                    //弹出键盘后目标item距离屏幕底部距离-原item距离屏幕底部
                    offset=(oldh-h+commentLayer.getHeight())-(oldh-clickbottom-title.getHeight());

                    //最后几条会被遮挡

                    //scrollBy会出现空白
//                    mlistView.scrollBy(0,offset);
                    //TODO 键盘遮挡最后几条
//                    if(cickposition+4<mlistView.getCount()){
//                        mlistView.scrollListBy(offset);
//                    }else{
//
//                    }

                    cickposition=-1;
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

        holder.name.setText("安卓" + datas[i]);

        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputBar();
                cickposition=position;
                //注意getChildAt获取的是布局内可见的item，如0为第一个可见的item
                clickbottom=mlistView.getChildAt(position-mlistView.getFirstVisiblePosition()).getBottom();
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

    private void hideInputBar() {
        commentLayer.setVisibility(View.GONE);
        keyboardUtils.closeSoftKeyboard(mContext);
    }

    private void initInputBar() {
        commentLayer = ((Activity) (mContext)).findViewById(R.id.input_layer);
        commentLayer.setVisibility(View.GONE);
    }
}
