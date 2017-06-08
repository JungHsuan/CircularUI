package com.jh.circularlist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
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

    private CircularTouchListener circularTouchListener;
    public float itemWith = 0;
    public float itemHeight = 0;
    public float layoutWidth;
    public float layoutHeight;
    public float layoutCenter_x;
    public float layoutCenter_y;
    public float radius;
    private double intervalDegree = Math.PI / 4;
    public ArrayList<View> itemViewList;

    /**
     * initialization
     */
    private void init() {

        // need to get the real height and width of view
        post(new Runnable() {
            @Override
            public void run() {
                Log.e("CircularListView", "get layout width and height");
                layoutWidth = getWidth();
                layoutHeight = getHeight();
                layoutCenter_x = layoutWidth / 2;
                layoutCenter_y = layoutHeight / 2;
                radius = layoutWidth / 3;
            }
        });

        itemViewList = new ArrayList<>();
        circularTouchListener = new CircularTouchListener();
        setOnTouchListener(circularTouchListener);
    }


    /**
     * set item click listener
     */
    public void setOnItemClickListener(CircularTouchListener.CircularItemClickListener listener) {
        circularTouchListener.setItemClickListener(listener);
    }


    /**
     * get interval degree of each view
     * @return degree
     */
    public double getIntervalDegree() {
        return intervalDegree;
    }

    /**
     * add your custom items into this view
     *
     * @param constructor initialize your views in constructor
     * @param degree      interval degree between each item
     */
    public void setCircularItem(CircularItemConstructor constructor, double degree) {

        // set display interval degree
        if (degree == 0)
            intervalDegree = 2.0f * Math.PI / (double) constructor.getCount();
        else
            intervalDegree = degree;

        // add all item view into parent layout
        itemViewList = constructor.getAllViews();
        for (int i = 0; i < constructor.getCount(); i++) {
            final int idx = i;
            final View item = constructor.getItemAt(i);
            addView(item);

            // wait for view drawn to get width and height
            item.post(new Runnable() {
                @Override
                public void run() {

                    item.setVisibility(View.INVISIBLE);
                    itemWith = item.getWidth();
                    itemHeight = item.getHeight();
                    /*
                     * position items according to circle formula
                     * margin left -> x = h + r * cos(theta)
                     * margin top -> y = k + r * sin(theta)
                     *
                     */
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) item.getLayoutParams();
                    params.setMargins(
                            (int)(layoutCenter_x - (itemWith / 2) + (radius * Math.cos(idx * intervalDegree))),
                            (int)(layoutCenter_y - (itemHeight / 2) + (radius * Math.sin(idx * intervalDegree))),
                            0,
                            0);
                    item.setLayoutParams(params);
                    item.setVisibility(View.VISIBLE);

                    //show animation
                    ScaleAnimation animation = new ScaleAnimation(0.2f, 1.0f, 0.2f, 1.0f,
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(500);
                    animation.setInterpolator(new OvershootInterpolator());
                    item.startAnimation(animation);

                }
            });

        }

    }


}
