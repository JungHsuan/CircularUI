package com.jh.circularlist;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

/**
 * Created by j.h. on 2017/4/22.
 * <p>
 * handle touch event of my circular ListView
 */

public class CircularTouchListener implements View.OnTouchListener {

    interface CircularItemClickListener {
        void onItemClick(View view, int index);
    }

    private CircularItemClickListener itemClickListener;
    private float init_x = 0;
    private float init_y = 0;
    private float pre_x = 0;
    private float pre_y = 0;
    private float cur_x = 0;
    private float cur_y = 0;
    private float move_x = 0;
    private float move_y = 0;
    private float minClickDistance = 30.0f;
    private float minMoveDistance = 30.0f;
    private float mMovingSpeed = 2000.0f;  // default is 2000, larger > faster
    private boolean isCircularMoving = false; // ensure that item click only triggered when it's not moving


    public void setItemClickListener(CircularItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final CircularListView circularView = (CircularListView) v;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                cur_x = event.getX();
                cur_y = event.getY();
                init_x = event.getX();
                init_y = event.getY();

            case MotionEvent.ACTION_MOVE:
                pre_x = cur_x;
                pre_y = cur_y;
                cur_x = event.getX();
                cur_y = event.getY();

                float diff_x = cur_x - pre_x;
                float diff_y = cur_y - pre_y;
                move_x = init_x - cur_x;
                move_y = init_y - cur_y;
                float moveDistance = (float) Math.sqrt(move_x * move_x + move_y * move_y);


                if (cur_y >= circularView.layoutCenter_y) diff_x = -diff_x;
                if (cur_x <= circularView.layoutCenter_x) diff_y = -diff_y;

                // should rotate the layout
                if (moveDistance > minMoveDistance) {
                    isCircularMoving = true;
                    CircularListView.MoveAccumulator += (diff_x + diff_y) / mMovingSpeed;

                    // calculate new position around circle
                    for (int i = 0; i < circularView.itemViewList.size(); i++) {
                        final int idx = i;
                        final View itemView = circularView.itemViewList.get(i);
                        itemView.post(new Runnable() {
                            @Override
                            public void run() {
                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                                        itemView.getLayoutParams();
                                params.setMargins(
                                        (int) (circularView.layoutCenter_x - (circularView.itemWith / 2) +
                                                (circularView.radius * Math.cos(idx * circularView.getIntervalDegree() +
                                                        CircularListView.MoveAccumulator * Math.PI * 2))),
                                        (int) (circularView.layoutCenter_y - (circularView.itemHeight / 2) +
                                                (circularView.radius * Math.sin(idx * circularView.getIntervalDegree() +
                                                        CircularListView.MoveAccumulator * Math.PI * 2))),
                                        0,
                                        0);
                                itemView.setLayoutParams(params);
                                itemView.requestLayout();
                            }
                        });
                    }
                }

                return true;

            case MotionEvent.ACTION_UP:

                // it is an click action if move distance < min distance
                moveDistance = (float) Math.sqrt(move_x * move_x + move_y * move_y);
                if (moveDistance < minClickDistance && !isCircularMoving) {
                    for (int i = 0; i < circularView.itemViewList.size(); i++) {
                        View view = circularView.itemViewList.get(i);
                        if (Utils.isTouchInside(cur_x, cur_y, view)) {
                            itemClickListener.onItemClick(view, i);

                            // set click animation
                            ScaleAnimation animation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f,
                                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                            animation.setDuration(300);
                            animation.setInterpolator(new OvershootInterpolator());
                            view.startAnimation(animation);

                            break;
                        }
                    }
                }
                isCircularMoving = false; // reset moving state when event ACTION_UP
                return true;
        }
        return false;
    }
}
