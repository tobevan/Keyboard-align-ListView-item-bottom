package com.qiyi.guianxiang.testlistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;

public class MainActivity extends Activity {
    private View commentLayer;
    private EditText comment_et;
    private CustumListView mListView;
    private int[] datas=new int[20];
    private myAdapter adapter;
    private View bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i <20; i++) {
            datas[i]=i+1;
        }
        commentLayer = findViewById(R.id.input_layer);
        comment_et = (EditText) findViewById(R.id.comment_et);
        mListView = (CustumListView) findViewById(R.id.mylist);

        bottom = LayoutInflater.from(this).inflate(R.layout.bottom, null, false);
        adapter=new myAdapter(this, mListView,datas);
        mListView.setAdapter(adapter);
        mListView.addFooterView(bottom);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                hideInputBar();
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

    }


    private void showInputBar() {
        commentLayer.setVisibility(View.VISIBLE);
        keyboardUtils.openSoftKeyboard(comment_et);
    }

    private void hideInputBar() {
        comment_et.setText("");
        commentLayer.setVisibility(View.GONE);
        keyboardUtils.closeSoftKeyboard(this);
    }
}
