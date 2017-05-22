package com.jh.circularlist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by jh on 2017/4/14.
 *
 *
 * According to Circle formula
 * x = h + r * cos(theta)
 * y = k + r * sin(theta)
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
    private RelativeLayout mView;
    private CircularTouchListener circularTouchListener;
    public float icon_with = 0;
    public float icon_height = 0;
    public float layoutWidth;
    public float layoutHeight;
    public float layoutCenter_x;
    public  float layoutCenter_y;
    public float radius;
    public double degree = Math.PI / 4;
    public ArrayList<View> mItemList;

    private void init(){
        mView = this;
        mItemList = new ArrayList<>();
        doLog("init");
        this.post(new Runnable() {
            @Override
            public void run() {
                layoutWidth = mView.getWidth();
                layoutHeight = mView.getHeight();
                layoutCenter_x = layoutWidth / 2;
                layoutCenter_y = layoutHeight / 2;
                radius = layoutWidth / 3;
            }
        });
        circularTouchListener = new CircularTouchListener(getContext());

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
                mView.setOnTouchListener(circularTouchListener);

                for(int i = 0 ; i < adapter.getCount(); i++) {
                    final int idx = i;
                    final View item = adapter.getItemAt(i);

                    //add view into parent
                    mView.addView(item);

                    // need to get item width and height
                    item.post(new Runnable() {
                        @Override
                        public void run() {
                            icon_with = item.getWidth();
                            icon_height = item.getHeight();
                            item.setTranslationX((float) (layoutCenter_x - (icon_with / 2 ) +
                                    (radius * Math.cos(idx * degree))));
                            item.setTranslationY((float) (layoutCenter_y - (icon_height / 2) +
                                    (radius * Math.sin(idx * degree))));
                            mItemList.add(item);
                        }
                    });
                }
            }
        });
    }


    /**
     * set item click listener
     */
    public void setOnItemClickListener(CircularTouchListener.CircularItemClickListener listener){
        circularTouchListener.setItemClickListener(listener);
    }
}
