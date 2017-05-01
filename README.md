# CircularList

Display views in circular way

![heart rate](https://raw.githubusercontent.com/JungHsuan/CircularList/master/screenShot/Screenshot_20170429-214403.png)
![heart rate](https://raw.githubusercontent.com/JungHsuan/CircularList/master/screenShot/Screenshot_20170429-214409.png)

## How To Use

1. Clone the project then there is an example in MainActivity: 
```shell
// usage sample
CircularListView circularListView = (CircularListView) findViewById(R.id.my_circular_list);
CircularAdapter adapter = new CircularAdapter(this,itemTitles);
circularListView.setAdapter(adapter);
```
2. Set CircularListView in your xml file:
```shell
<com.jh.circularlist.CircularListView
        android:layout_centerInParent="true"
        android:id="@+id/my_circular_list"
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:background="@drawable/circle_background"
        />
```
3. Customize your items in CircularAdapter
Example code for initialize Adapter:

Here, the 'itemTitles' is just a ArrayList of String, you can modify it whatever you want.
```shell
ArrayList<String> itemTitles = new ArrayList<>();
for(int i = 0 ; i < 10 ; i ++)
        itemTitles.add(String.valueOf(i));
CircularAdapter adapter = new CircularAdapter(this,itemTitles);
```

## CircularTlouchListener
This custom listener implement the item click event and cicular translation.
