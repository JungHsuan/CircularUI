package com.jh.circularlist;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;

public class CircularItemTouchListener implements View.OnTouchListener {

    private float init_x = 0;
    private float init_y = 0;
    private float pre_x = 0;
    private float pre_y = 0;
    private float cur_x = 0;
    private float cur_y = 0;
    private CircularTouchListener parentTouchListener;
    private View parentView;
    private Context context;

    public CircularItemTouchListener(Context c,View view ,CircularTouchListener listener){
        this.parentTouchListener = listener;
        this.parentView = view;
        this.context = c;
    }

    private MotionEvent touchDown;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cur_x = event.getX();
                cur_y = event.getY();
                init_x = event.getX();
                init_y = event.getY();
                touchDown = event;

            case MotionEvent.ACTION_MOVE:
                pre_x = cur_x;
                pre_y = cur_y;
                cur_x = event.getX();
                cur_y = event.getY();
                float diff_x = cur_x - pre_x;
                float diff_y = cur_y - pre_y;
                float move_x = init_x - cur_x;
                float move_y = init_y - cur_y;
                float moveDistance = (float) Math.sqrt(move_x * move_x + move_y * move_y);
                //Log.d("ItemTouchListener","move distance:" + moveDistance);
                if (moveDistance > 10){
                    System.out.println(touchDown.getAction());
                    parentTouchListener.onTouch(parentView ,touchDown);
                    return false;
                }
                else {
                    return true;
                }

            case MotionEvent.ACTION_UP:
                move_x = init_x - cur_x;
                move_y = init_y - cur_y;
                moveDistance = (float) Math.sqrt(move_x * move_x + move_y * move_y);
                if(moveDistance <= 10){
                    Log.d("ItemTouchListener", "item has  been clicked!");

                    ScaleAnimation animation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f,
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(500);

//                    ValueAnimator valueAnimator = new ValueAnimator();
//                    valueAnimator.setDuration(300);
//                    valueAnimator.setFloatValues(0.5f,1.1f,1.0f);
//                    valueAnimator.start();

                    v.startAnimation(animation);
                    return true;
                }
                else return false;
        }
        return false;
    }
}
