package com.revosleap.actionfab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

        fabLayout.mainActionFab(new ActionItem()
                .setNormalColor(Color.MAGENTA)
        .setResColor(Color.WHITE));





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
