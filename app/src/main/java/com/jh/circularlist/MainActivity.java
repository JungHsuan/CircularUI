package com.jh.circularlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircularListView circularListView = (CircularListView) findViewById(R.id.my_circular_list);

        ArrayList<View> mItems = new ArrayList<>();
        for(int i = 0; i < 8 ; i ++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);
            mItems.add(imageView);
        }
        circularListView.addItems(mItems);

    }
}
