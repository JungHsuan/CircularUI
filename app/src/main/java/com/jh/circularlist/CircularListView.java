package com.jh.circularlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by jh on 2017/4/14
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

    private RelativeLayout mCircularLayout;
    private CircularTouchListener circularTouchListener;
    public float itemWith = 0;
    public float itemHeight = 0;
    public float layoutWidth;
    public float layoutHeight;
    public float layoutCenter_x;
    public float layoutCenter_y;
    public float radius;
    private double intervalDegree = Math.PI / 4;
    public ArrayList<View> mItemList;

    /**
        initialization
     */
    private void init() {
        mCircularLayout = this;
        mItemList = new ArrayList<>();
        this.post(new Runnable() {
            @Override
            public void run() {
                layoutWidth = mCircularLayout.getWidth();
                layoutHeight = mCircularLayout.getHeight();
                layoutCenter_x = layoutWidth / 2;
                layoutCenter_y = layoutHeight / 2;
                radius = layoutWidth / 3;
            }
        });
        circularTouchListener = new CircularTouchListener();
        this.setOnTouchListener(circularTouchListener);
    }


    /**
     * set item click listener
     */
    public void setOnItemClickListener(CircularTouchListener.CircularItemClickListener listener) {
        circularTouchListener.setItemClickListener(listener);
    }



    public double getIntervalDegree(){
        return intervalDegree;
    }

    /**
     * add your custom items into this view
     * @param constructor initialize your views in constructor
     * @param degree interval degree between each item
     */
    public void setCircularItem(final CircularItemConstructor constructor, double degree) {

        if(degree == 0) intervalDegree = 2.0f * Math.PI / (double)constructor.getCount();
        else intervalDegree = degree;

        mCircularLayout.post(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < constructor.getCount(); i++) {
                    final int idx = i;
                    final View item = constructor.getItemAt(i);
                    item.setClickable(false);
                    mCircularLayout.addView(item);

                    /*
                     * position items according to circle formula
                     * x = h + r * cos(theta)
                     * y = k + r * sin(theta)
                     *
                     */
                    item.post(new Runnable() {
                        @Override
                        public void run() {
                            itemWith = item.getWidth();
                            itemHeight = item.getHeight();
                            item.setTranslationX((float) (layoutCenter_x - (itemWith / 2) +
                                    (radius * Math.cos(idx * intervalDegree))));
                            item.setTranslationY((float) (layoutCenter_y - (itemHeight / 2) +
                                    (radius * Math.sin(idx * intervalDegree))));
                            mItemList.add(item);
                        }
                    });
                }
            }
        });
    }


}
