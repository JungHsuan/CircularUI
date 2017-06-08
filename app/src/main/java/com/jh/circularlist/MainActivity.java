package com.jh.circularlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // simple text item with numbers 0 ~ 9
        ArrayList<String> itemTitles = new ArrayList<>();
        for(int i = 0 ; i < 12 ; i ++){
            itemTitles.add(String.valueOf(i));
        }


        // usage sample
        CircularListView circularListView = (CircularListView) findViewById(R.id.my_circular_list);
        circularListView.setCircularItem(new ItemConstructor(getLayoutInflater(), itemTitles),0);
        circularListView.setOnItemClickListener(new CircularTouchListener.CircularItemClickListener() {
            @Override
            public void onItemClick(View view, int index) {
                Toast.makeText(MainActivity.this,
                        "view :" + index + " is clicked!",
                        Toast.LENGTH_SHORT).show();

                // click animation
                ScaleAnimation animation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(500);
                view.startAnimation(animation);
            }
        });
    }



    // you should extends CircularItemConstructor to add your custom item
    private class ItemConstructor extends CircularItemConstructor{

        private ArrayList<String> mItems;
        private LayoutInflater mInflater;
        private ArrayList<View> mItemViews;

        public ItemConstructor(LayoutInflater inflater, ArrayList<String> items){
            this.mItemViews = new ArrayList<>();
            this.mItems = items;
            this.mInflater = inflater;

            for(final String s : mItems){
                View view = mInflater.inflate(R.layout.view_circular_item, null);
                TextView itemView = (TextView) view.findViewById(R.id.bt_item);
                itemView.setText(s);
                mItemViews.add(view);
            }
        }

        @Override
        public ArrayList<View> getAllViews() {
            return mItemViews;
        }

        @Override
        public int getCount() {
            return mItemViews.size();
        }

        @Override
        public View getItemAt(int i) {
            return mItemViews.get(i);
        }
    }

}
