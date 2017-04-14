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
    private float mLayoutWidth;
    private float mLayoutHeight;
    private float mLayoutCenter_x;
    private float mLayoutCenter_y;
    private float radius;
    private float iconSize = 50;
    private double degree = Math.PI / 4;

    private void init(){
        mItemList = new ArrayList<>();
        doLog("init");
        this.post(new Runnable() {
            @Override
            public void run() {
                mLayoutWidth = mView.getWidth();
                mLayoutHeight = mView.getHeight();
                mLayoutCenter_x = mLayoutWidth / 2;
                mLayoutCenter_y = mLayoutHeight / 2;
                radius = mLayoutWidth / 3;
                mView.setOnTouchListener(new CircularTouchListener());
                doLog("mLayoutWidth=" + mLayoutWidth + ", mLayoutHeight=" + mLayoutHeight);

            }
        });

    }

    /**
     * save all items in an array. Item may be a view
     */
    private ArrayList<View> mItemList;



    /**
     * add item into the list
     *
     */
    public void addItems(final ArrayList<View> items){

        degree = 2* Math.PI / items.size();
        mView.post(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i <items.size() ; i++) {

                    //doLog("radius=" + radius);
                    //doLog("mLayoutCenter_x=" + mLayoutCenter_x + ", mLayoutCenter_y=" + mLayoutCenter_y);

                    View item = items.get(i);
                    mView.addView(item);
                    item.setTranslationX((float) (mLayoutCenter_x - iconSize + (radius * Math.cos(i * degree))));
                    item.setTranslationY((float) (mLayoutCenter_y - iconSize + (radius * Math.sin(i * degree))));
                    mItemList.add(item);

                }
            }
        });
    }


    /**
     * control touch event
     *
     */

    class CircularTouchListener implements View.OnTouchListener{

        private float pre_x = 0;
        private float pre_y = 0;
        private float cur_x = 0;
        private float cur_y = 0;
        private float count = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    doLog("ACTION_DOWN");
                    cur_x = event.getX();
                    cur_y = event.getY();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    doLog("ACTION_MOVE");
                    pre_x = cur_x;
                    pre_y = cur_y;
                    cur_x = event.getX();
                    cur_y = event.getY();
                    float diff_x = cur_x - pre_x;
                    float diff_y = cur_y - pre_y;

                    if (cur_y >= mLayoutCenter_y) diff_x = -diff_x;
                    if (cur_x <= mLayoutCenter_x) diff_y = -diff_y;
                    count += (diff_x + diff_y) / 2000;

                    for (int i = 0; i < mItemList.size(); i++) {

                        mItemList.get(i).setTranslationX((float) ((mLayoutCenter_x - (iconSize)
                                + radius * Math.cos(i * degree + count * Math.PI * 2))));
                        mItemList.get(i).setTranslationY((float) ((mLayoutCenter_y - (iconSize)
                                + radius * Math.sin(i * degree + count * Math.PI * 2))));
                    }

                    return true;
            }
            return false;
        }
    }

}
