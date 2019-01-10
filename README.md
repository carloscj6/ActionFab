# ActionFab
Simple to use Floating Action Menu.

[ ![Download](https://api.bintray.com/packages/carloscj6/UI/ActionFab/images/download.svg) ](https://bintray.com/carloscj6/UI/ActionFab/_latestVersion)

## Screenshot
<img src="https://github.com/carloscj6/ActionFab/blob/master/app/Screenshots/ezgif.com-optimize.gif" width="300px">

## Usage
First in your buld.gradle file include this dependency

```gradle
dependencies {
    implementation 'com.revosleap.actionfab:actionfab:1.0.01'
}
``` 
and in your `layout.xml` set the parent layout as:

```xml
    <com.revosleap.actionfab.ActionFabLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    </com.revosleap.actionfab.ActionFabLayout>
 ```
    
 and then in your `activity` file set the floating action buttons as below:
 
 ```java
    public class MainActivity extends AppCompatActivity implements ActionFabLayout.OnActionFabItemClick {
    private ActionFabLayout fabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabLayout= findViewById(R.id.fabLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fabLayout.setActionFabItemClick(this);
        List<ActionItem> actionItems= new ArrayList<>();

        actionItems.add(new ActionItem<Integer>()
        .setNormalColor(Color.GREEN)
        .setPressedColor(Color.BLUE)
        .setResColor(Color.WHITE)
        .setResId(R.drawable.ic_photo));
        actionItems.add(new ActionItem<Integer>()
                .setNormalColor(Color.WHITE)
                .setPressedColor(Color.BLUE)
                .setResColor(Color.WHITE)
                .setResId(R.drawable.ic_photo));
        actionItems.add(new ActionItem<Integer>()
                .setNormalColor(Color.LTGRAY)
                .setPressedColor(Color.BLUE)
                .setResColor(Color.WHITE)
                .setResId(R.drawable.ic_photo));
        actionItems.add(new ActionItem<Integer>()
                .setNormalColor(Color.RED)
                .setPressedColor(Color.BLUE)
                .setResColor(Color.WHITE)
                .setResId(R.drawable.ic_photo));
        fabLayout.setItems(actionItems);

       
    }

    @Override
    public void onBackPressed() {

        if (fabLayout.isLayoutOpen){
            fabLayout.hideActionFabs();
        }else {
            super.onBackPressed();
        }
    }
 @Override
    public void onActionFabItemClick(int position, ActionItem item) {
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}
```
As per now, the library works well with upto 5 items but I hope it will be fun to use.
You can cusomize the main floating action button as per your preferences but it cannot take transparent color value `0`
and instead it will use your accent color.
You can do it as:
```java
 fabLayout.mainActionFab(new ActionItem()
                .setNormalColor(Color.MAGENTA)
        .setResColor(Color.WHITE));
  ```
## Bugs
There can be some issue with the library not working on some instances, In the case please manually download and import the module from [here](https://github.com/carloscj6/ActionFab/tree/master/actionfab) or simply just download this project as zip and use the ActionFab module. 
Am working on the best fix possible. :hushed: :hushed: :stuck_out_tongue:
## Contributions
Feel free to fork and  make pull requests. 

##
Coded with :blue_heart: by [revosleap](https://revosleap.com/).

