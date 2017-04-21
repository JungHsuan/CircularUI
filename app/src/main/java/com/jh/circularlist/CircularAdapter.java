package com.jh.circularlist;

import android.content.Context;
import android.service.carrier.CarrierService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jh on 2017/4/15.
 *
 * using this custom adapter(not really an adapter) to set your items
 */

public class CircularAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<View> mItemViews;

    public int getCount() { return mItemViews.size(); }
    public View getItemAt(int position) { return mItemViews.get(position); }


    /**
     * constructor initialize items, using string array in this example
     */
    public CircularAdapter(Context context, ArrayList<String> data){

        this.mContext = context;
        mItemViews = new ArrayList<>();
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // just for demo fill with drawable
        ArrayList<Integer> drawableList = new ArrayList<>();
        drawableList.add(R.drawable.android);
        drawableList.add(R.drawable.camera);
        drawableList.add(R.drawable.cart);
        drawableList.add(R.drawable.cloud);
        drawableList.add(R.drawable.duck);
        drawableList.add(R.drawable.email);
        drawableList.add(R.drawable.facebook_box);
        drawableList.add(R.drawable.ghost);
        drawableList.add(R.drawable.home);
        drawableList.add(R.drawable.lightbulb);
        drawableList.add(R.drawable.message);


        // for each data, display using an custom view
        for(final String s : data){
            Log.d("TAG", s);
            View view = mInflater.inflate(R.layout.view_circular_item, null);
            ImageView image = (ImageView)view.findViewById(R.id.item_icon);
            image.setImageResource(drawableList.get(data.indexOf(s)));
            TextView title = (TextView) view.findViewById(R.id.item_title);
            title.setText(s);
            view.setOnTouchListener(new View.OnTouchListener() {

                private float init_x = 0;
                private float init_y = 0;
                private float cur_x = 0;
                private float cur_y = 0;
                float moveDistance;

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            init_x = event.getX();
                            init_x = event.getY();
                            cur_x = event.getX();
                            cur_y = event.getY();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            cur_x = event.getX();
                            cur_y = event.getY();
                            float move_x = init_x - cur_x;
                            float move_y = init_y - cur_y;
                            moveDistance = (float)Math.sqrt(move_x*move_x + move_y*move_y);
                            Log.d("TAG","moveDistance = " + moveDistance);
                            if(moveDistance > 100) {
                                break;
                            }
                            else return true;
                        case MotionEvent.ACTION_UP:
                            Toast.makeText(mContext, "This is item:" + s,Toast.LENGTH_SHORT).show();
                            return false;
                    }
                    return false;
                }
            });

            mItemViews.add(view);
            Log.d("TAG","size=" + mItemViews.size());
        }

    }

}
