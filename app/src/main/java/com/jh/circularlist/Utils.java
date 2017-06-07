package com.jh.circularlist;

import android.view.View;

/**
 * Created by j.h. on 2017/4/22.
 *
 * computation tools
 */

 class Utils {
    /**
     *
     * @param x touch position of x
     * @param y touch position of y
     * @param view the view that you want to know if we touch inside it
     * @return true or false whether we are actually touch the view
     */
    public static boolean isTouchInside(float x, float y, View view){
        float left = view.getX();
        float top  = view.getY();
        float wid = view.getWidth();
        float h = view.getHeight();
        return (x > left && x < left + wid && y > top && y < top+h);
    }
}
