package com.jh.circularlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class MainActivity extends AppCompatActivity {

    private CircularItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // simple text item with numbers 0 ~ 9
        ArrayList<String> itemTitles = new ArrayList<>();
        for(int i = 0 ; i < 6 ; i ++){
            itemTitles.add(String.valueOf(i));
        }


        // usage sample
        CircularListView circularListView = (CircularListView) findViewById(R.id.my_circular_list);
        adapter = new CircularItemAdapter(getLayoutInflater(), itemTitles);
        circularListView.setAdapter(adapter);
        circularListView.setOnItemClickListener(new CircularTouchListener.CircularItemClickListener() {
            @Override
            public void onItemClick(View view, int index) {
                Toast.makeText(MainActivity.this,
                        "view at index " + index + " is clicked!",
                        Toast.LENGTH_SHORT).show();
            }
        });


        // remove item example
        Button btRemoveItem = (Button) findViewById(R.id.bt_remove_item);
        btRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeItemAt(0);
            }
        });


        // add item example
        Button btAddItem = (Button) findViewById(R.id.bt_add_item);
        btAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.view_circular_item, null);
                TextView itemView = (TextView) view.findViewById(R.id.bt_item);
                itemView.setText(String.valueOf(adapter.getCount() + 1));
                adapter.addItem(view);
            }
        });
    }



    // you should extends CircularAdapter to add your custom item
    private class CircularItemAdapter extends CircularAdapter {

        private ArrayList<String> mItems;
        private LayoutInflater mInflater;
        private ArrayList<View> mItemViews;

        public CircularItemAdapter(LayoutInflater inflater, ArrayList<String> items){
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

        @Override
        public void removeItemAt(int i) {
            if(mItemViews.size() > 0) {
                mItemViews.remove(i);
                notifyItemChange();
            }
        }

        @Override
        public void addItem(View view) {
            mItemViews.add(view);
            notifyItemChange();
        }
    }

}
