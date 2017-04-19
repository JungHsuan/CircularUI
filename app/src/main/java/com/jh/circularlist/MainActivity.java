package com.jh.circularlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> itemTitles = new ArrayList<>();
        for(int i = 0 ; i < 8 ; i ++){
            itemTitles.add(String.valueOf(i));
        }

        CircularListView circularListView = (CircularListView) findViewById(R.id.my_circular_list);
        CircularAdapter adapter = new CircularAdapter(this,itemTitles);
        circularListView.setAdapter(adapter);

    }
}
