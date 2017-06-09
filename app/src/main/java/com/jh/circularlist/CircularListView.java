package com.jh.circularlist;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by jh on 2017/4/14
 */

public class CircularListView extends RelativeLayout implements CircularAdapter.CircularItemChangeListener {

    private CircularTouchListener circularTouchListener;
    public float itemWith = 0;
    public float itemHeight = 0;
    public float layoutWidth;
    public float layoutHeight;
    public float layoutCenter_x;
    public float layoutCenter_y;
    public float radius;
    private double intervalDegree = Math.PI / 4;
    private double oldIntervalDegree = Math.PI / 4;
    public ArrayList<View> itemViewList;
    private CircularAdapter circularAdapter;
    public static float MoveAccumulator = 0;

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


    @Override
    public void onCircularItemChange() {
        setItemPosition();
    }

    /**
     * add your custom items into this view
     * @param adapter initialize your views in adapter
     *
     */
    public void setAdapter(CircularAdapter adapter) {
        // register item change listener
        this.circularAdapter = adapter;
        circularAdapter.setOnItemChangeListener(this);
        setItemPosition();
    }


    /**
     * set the circular position of each item
     *
     */
    private void setItemPosition(){


        int updateChildCount = circularAdapter.getCount();
        int existChildCount = getChildCount();

        // set display interval degree
        if(existChildCount == 0) oldIntervalDegree = updateChildCount;
        else  oldIntervalDegree = 2.0f * Math.PI / (double) existChildCount;
        intervalDegree = 2.0f * Math.PI / (double) updateChildCount;


        // clear all child before add
        removeAllViews();

        // add all item view into parent layout
        itemViewList = circularAdapter.getAllViews();

        for (int i = 0; i < circularAdapter.getCount(); i++) {
            final int idx = i;
            final View item = circularAdapter.getItemAt(i);
            addView(item);
            item.setVisibility(View.INVISIBLE);

            // wait for view drawn to get width and height
            item.post(new Runnable() {
                @Override
                public void run() {

                    itemWith = item.getWidth();
                    itemHeight = item.getHeight();
                    /*
                     * position items according to circle formula
                     * margin left -> x = h + r * cos(theta)
                     * margin top -> y = k + r * sin(theta)
                     *
                     */
                    ValueAnimator valueAnimator = new ValueAnimator();
                    valueAnimator.setFloatValues((float)oldIntervalDegree, (float)intervalDegree);
                    valueAnimator.setDuration(500);
                    valueAnimator.setInterpolator(new OvershootInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (Float) (animation.getAnimatedValue());

                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) item.getLayoutParams();
                            params.setMargins(
                                    (int)(layoutCenter_x - (itemWith / 2) + (radius *
                                            Math.cos(idx * value + CircularListView.MoveAccumulator * Math.PI * 2))),
                                    (int)(layoutCenter_y - (itemHeight / 2) + (radius *
                                            Math.sin(idx * value + CircularListView.MoveAccumulator * Math.PI * 2))),
                                    0,
                                    0);
                            item.setLayoutParams(params);
                        }
                    });
                    valueAnimator.start();


                    item.setVisibility(View.VISIBLE);

                    //show animation
                    /*
                    ScaleAnimation animation = new ScaleAnimation(0.2f, 1.0f, 0.2f, 1.0f,
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(500);
                    animation.setInterpolator(new OvershootInterpolator());
                    item.startAnimation(animation);
                    */

                }
            });

        }
    }


}
