package com.jh.circularlist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
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
        for(int i = 0 ; i < 10 ; i ++){
            itemTitles.add(String.valueOf(i));
        }


//        // just for demo fill with drawable
//        ArrayList<Integer> drawableList = new ArrayList<>();
//        drawableList.add(R.drawable.android);
//        drawableList.add(R.drawable.camera);
//        drawableList.add(R.drawable.cart);
//        drawableList.add(R.drawable.cloud);
//        drawableList.add(R.drawable.duck);
//        drawableList.add(R.drawable.email);
//        drawableList.add(R.drawable.facebook_box);
//        drawableList.add(R.drawable.ghost);
//        drawableList.add(R.drawable.home);
//        drawableList.add(R.drawable.lightbulb);
//        drawableList.add(R.drawable.message);


        // usage sample
        CircularListView circularListView = (CircularListView) findViewById(R.id.my_circular_list);
        circularListView.setCircularItem(new ItemConstructor(getApplicationContext(), itemTitles),0);
        circularListView.setOnItemClickListener(new CircularTouchListener.CircularItemClickListener() {
            @Override
            public void onItemClick(View view, int index) {
                Toast.makeText(MainActivity.this,
                        "view :" + index + " is clicked!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class ItemConstructor extends CircularItemConstructor{

        private ArrayList<String> mItems;
        private LayoutInflater mInflater;
        private ArrayList<View> mItemViews;

        public ItemConstructor(Context context, ArrayList<String> items){
            this.mItemViews = new ArrayList<>();
            this.mItems = items;
            this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
