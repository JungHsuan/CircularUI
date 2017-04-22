package com.jh.circularlist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by jh on 2017/4/14.
 *
 */

public class CircularListView extends RelativeLayout {


    public CircularListView(Context context) {
        super(context);
        init();
    }

    public CircularListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * if is debug mode
     */
    private boolean debugMode = true;
    private void doLog(String msg){
        if(debugMode) Log.d("CircularListView", msg);
    }



    /**
     * get layout parameter
     */
    private RelativeLayout mView = this;
    public float iconSize = 0;
    public float LayoutWidth;
    public float LayoutHeight;
    public float LayoutCenter_x;
    public  float LayoutCenter_y;
    public float radius;
    public double degree = Math.PI / 4;
    public ArrayList<View> mItemList;

    private void init(){
        mItemList = new ArrayList<>();
        doLog("init");
        this.post(new Runnable() {
            @Override
            public void run() {
                LayoutWidth = mView.getWidth();
                LayoutHeight = mView.getHeight();
                LayoutCenter_x = LayoutWidth / 2;
                LayoutCenter_y = LayoutHeight / 2;
                radius = LayoutWidth / 3;
                doLog("mLayoutWidth=" + LayoutWidth + ", mLayoutHeight=" + LayoutHeight);

            }
        });

    }


    /**
     * add item into the list
     *
     */
    public void setAdapter(final CircularAdapter adapter){

        degree = 2* Math.PI / adapter.getCount();
        mView.post(new Runnable() {
            @Override
            public void run() {

                // set touch listener
                mView.setOnTouchListener(new CircularTouchListener(getContext()));

                for(int i = 0 ; i < adapter.getCount(); i++) {
                    //doLog("mLayoutCenter_x=" + mLayoutCenter_x + ", mLayoutCenter_y=" + mLayoutCenter_y);
                    View item = adapter.getItemAt(i);
                    //item.setClickable(false);
                    mView.addView(item);
                    //iconSize = item.getLayoutParams().width;
                    //doLog("iconSize=" + iconSize);
                    item.setTranslationX((float) (LayoutCenter_x - iconSize + (radius * Math.cos(i * degree))));
                    item.setTranslationY((float) (LayoutCenter_y - iconSize + (radius * Math.sin(i * degree))));
                    mItemList.add(item);



                }
            }
        });
    }
}
