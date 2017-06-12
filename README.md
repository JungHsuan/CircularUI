# CircularUI

Display views in circular shape with smooth scroll and item click action. 

![demo video](https://raw.githubusercontent.com/JungHsuan/CircularList/master/screenShot/ezgif-1-c709720d20.gif)
![demo video](https://raw.githubusercontent.com/JungHsuan/CircularList/master/screenShot/ezgif-1-f4c73d896d_radius.gif)


## How To Use
Step 1. Add the JitPack repository to your gradle file
```shell
allprojects {
        repositories {
                ...
                maven { url 'https://jitpack.io' }
        }
}
```
Step 2. Add the dependency
```shell
dependencies {
        compile 'com.github.JungHsuan:CircularList:1.1'
}
```

Step 3. Add CircularListView in your layout xml
```shell
<com.jh.circularlist.CircularListView
        android:id="@+id/my_circular_list"
        android:layout_width="300dp"
        android:layout_height="300dp"/>
```

Step 4. Create an custom adaper extend from CircularAdapter
```shell
    // you should extends CircularAdapter to add your custom item
    private class CircularItemAdapter extends CircularAdapter {

        private ArrayList<String> mItems;       // custom data, here we simply use string
        private LayoutInflater mInflater;       
        private ArrayList<View> mItemViews;     // to store all list item 

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
                // remove from view list
                mItemViews.remove(i);
                // this is necessary to call to notify change
                notifyItemChange(); 
            }
        }

        @Override
        public void addItem(View view) {
            // add to view list
            mItemViews.add(view);
            // // this is necessary to call to notify change
            notifyItemChange();
        }
    }
```


Step 4. inistialize CircularListView in your activity
```shell

// simple text item with numbers 0 ~ 9
ArrayList<String> itemTitles = new ArrayList<>();
for(int i = 0 ; i < 6 ; i ++){
        itemTitles.add(String.valueOf(i));
}

CircularListView circularListView = (CircularListView) findViewById(R.id.my_circular_list);
CircularItemAdapter adapter = new CircularItemAdapter(getLayoutInflater(), itemTitles);
circularListView.setOnItemClickListener(new CircularTouchListener.CircularItemClickListener() {
            @Override
            public void onItemClick(View view, int index) {
            }
        });
```

## Add an item into list
```shell
// inflate a layout
View view = getLayoutInflater().inflate(R.layout.view_circular_item, null);
TextView itemView = (TextView) view.findViewById(R.id.bt_item);
itemView.setText(String.valueOf(adapter.getCount() + 1));

// add to list
adapter.addItem(view);
```

## Remove an item from list
```shell
adapter.removeItemAt(index);
```

## Set circle radius
Value smaller than zero will be set as zero.
```shell
circularListView.setRadius(float r);
```



