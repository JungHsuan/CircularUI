package com.jh.circularlist;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by j.h. on 2017/4/22.
 *
 * handle touch event of my circular ListView
 */

class CircularTouchListener implements View.OnTouchListener {

    interface CircularItemClickListener{
        void onItemClick(View view, int index);
    }

    private CircularItemClickListener itemClickListener;
    private Context mContext;
    private float init_x = 0;
    private float init_y = 0;
    private float pre_x = 0;
    private float pre_y = 0;
    private float cur_x = 0;
    private float cur_y = 0;
    private float count = 0;
    private float minClickDistance = 50.0f;
    private float mMovingSpeed = 2000.0f;  // default is 2000, larger -> slower

    /**
     * constructor
     */
    public CircularTouchListener(Context context){
        this.mContext = context;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        CircularListView circleView = (CircularListView) v;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //doLog("ACTION_DOWN");
                cur_x = event.getX();
                cur_y = event.getY();
                init_x = event.getX();
                init_y = event.getY();
                return true;

            /**
             *  do something when finger moving on the circle
             */
            case MotionEvent.ACTION_MOVE:
                //doLog("ACTION_MOVE");
                pre_x = cur_x;
                pre_y = cur_y;
                cur_x = event.getX();
                cur_y = event.getY();
                float diff_x = cur_x - pre_x;
                float diff_y = cur_y - pre_y;

                if (cur_y >= circleView.layoutCenter_y) diff_x = -diff_x;
                if (cur_x <= circleView.layoutCenter_x) diff_y = -diff_y;
                count += (diff_x + diff_y) / mMovingSpeed;

                // calculate new position around circle
                for (int i = 0; i < circleView.mItemList.size(); i++) {
                    circleView.mItemList.get(i).setTranslationX(
                            (float) ((circleView.layoutCenter_x - (circleView.icon_with / 2 ) +
                                    circleView.radius * Math.cos(i * circleView.degree + count * Math.PI * 2))));
                    circleView.mItemList.get(i).setTranslationY(
                            (float) ((circleView.layoutCenter_y - (circleView.icon_height /2) +
                                    circleView.radius * Math.sin(i * circleView.degree + count * Math.PI * 2))));
                }

                return true;

            case MotionEvent.ACTION_UP:
                //doLog("ACTION_UP");

                /**
                 * need to know if it is a click
                 */
                float move_x = init_x - cur_x;
                float move_y = init_y - cur_y;
                float moveDistance = (float) Math.sqrt(move_x * move_x + move_y * move_y);
                if (moveDistance < minClickDistance) {
                    for (int i = 0; i < circleView.mItemList.size(); i++) {
                        View tmp = circleView.mItemList.get(i);
                        if (Utils.isTouchInside(cur_x, cur_y, tmp)) {
                            itemClickListener.onItemClick(tmp, i);
                            break;
                        }
                    }
                }
                return true;
        }
        return false;
    }

    public void setItemClickListener(CircularItemClickListener listener){
        this.itemClickListener = listener;
    }
}
