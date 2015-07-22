# AdjustableLayout

By Extending Linear Layout and overriding addView() function , created such a layout where any view can be added horizontally. When view does not fit in the screen , it automatically occupies space from next line auto adjusting it's height.

# Screen shots

![screenshot from 2015-07-22 14 28 02](https://cloud.githubusercontent.com/assets/4836122/8823135/2e6a785e-3088-11e5-94a7-563a9f614360.png)
![screenshot from 2015-07-22 14 54 01](https://cloud.githubusercontent.com/assets/4836122/8823136/2e6dfb78-3088-11e5-9a64-7ebeab2462de.png)
![screenshot from 2015-07-22 15 11 46](https://cloud.githubusercontent.com/assets/4836122/8823137/2e7545c2-3088-11e5-8138-278bb0d9cecd.png)
![screenshot from 2015-07-22 15 14 55](https://cloud.githubusercontent.com/assets/4836122/8823138/2e7665d8-3088-11e5-98b5-db08dbf11649.png)
![screenshot from 2015-07-22 15 25 37](https://cloud.githubusercontent.com/assets/4836122/8823139/2e78037a-3088-11e5-8582-cec3c6b15ada.png)

# Instructions
Using Adjustable layout.
</br>
In your `activity_main.xml`
``` <rana.com.adjustablelayout.AdjustableLayout
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
``` 
Create your own layout eg. `view_chip_text.xml`
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="@drawable/chips"
    android:gravity="center"
    android:orientation="horizontal"
    android:padding="3dp">

    <TextView
        android:textColor="#FFFFFF"
        android:id="@+id/tvName"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="14sp"
        android:text="name"/>

    <ImageView
        android:id="@+id/ivRemove"
        android:layout_marginLeft="5dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/abc_ic_clear_mtrl_alpha" />
</LinearLayout>
```
Drawable file `chips.xml`
```
<?xml version="1.0" encoding="utf-8"?>
<shape
    android:shape="rectangle"
    xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="@color/colorPrimary"/>
    <corners android:radius="5dp"/>
</shape>
```

In your java code

```
 adjustableLayout = (AdjustableLayout)findViewById(R.id.container);
 addChipsView();
 /**
     * Using view_chip_text layout
     */
    private void addChipsView() {
        String name = etEnterName.getText().toString();
        if (!TextUtils.isEmpty(name)){
            final View newView = LayoutInflater.from(this).inflate(R.layout.view_chip_text,null);
            TextView tvName = (TextView)newView.findViewById(R.id.tvName);
            ImageView ivRemove = (ImageView)newView.findViewById(R.id.ivRemove);
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adjustableLayout.removeView(newView);
                }
            });
            tvName.setText(name);
            adjustableLayout.addView(newView);
        }else {
            Toast.makeText(this,"Enter some text",Toast.LENGTH_SHORT).show();
        }
    }
```
