package com.revosleap.actionfab;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.List;

public class ActionFabLayout extends FrameLayout implements View.OnClickListener {
    private Context context;
    private int ID=1;
    private final FloatingActionButton fab;
    private Point[] vertices;
    private FloatingActionButton[] buttons;
    private final int width;
    private final int radius;
    private final int ANIMATION_DURATION=300;
    private int startPositionX=0;
    private int startPositionY=0;
    private int NUM_OF_SIDES= 1;
    private final int POSITION_CORRECTION=11;
    private final int[] enterDelay= {80, 120, 160, 40, 0};
    private final int[] exitDelay = {80, 40, 0, 120, 160};
    public boolean isLayoutOpen=false;
    private final FrameLayout frameLayout=this;
    private List<ActionItem> items;
    private OnActionFabItemClick actionFabItemClick;
    public interface OnActionFabItemClick<T>{
        void onActionFabItemClick (int position, ActionItem<T> item);
    }
    public void setActionFabItemClick(OnActionFabItemClick onActionFabItemClick){
        this.actionFabItemClick=onActionFabItemClick;
    }

    public ActionFabLayout(@NonNull Context context) {
        this(context,null);
        this.context=context;
    }

    public ActionFabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        this.context= context;
    }

    public ActionFabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context= context;
        inflate(context,R.layout.action_fab_layout,this);
        fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(this);
        int height = (int) getResources().getDimension(R.dimen.button_height);
        width = (int) getResources().getDimension(R.dimen.button_width);
        radius = (int) getResources().getDimension(R.dimen.radius);


    }
    public void setItems(List<ActionItem> actionItems){
        if (actionItems!=null){
            if (actionItems.size()<=5){
                this.items=actionItems;
                NUM_OF_SIDES=items.size();
                setActionFab();
            }else Log.w("ActionFab",getContext().getString(R.string.excess_warning));

        }
    }
    public void hideActionFabs(){
        if (isLayoutOpen){
            for (int i=0;i<buttons.length;i++){
                playExitAnimation(buttons[i],i);
                rotateFoward(fab,-90f);
            }
            isLayoutOpen=false;
        }
    }
    public void mainActionFab(ActionItem mainFab){
        if (mainFab!=null){
            if (mainFab.getResId()!=0){
                fab.setImageResource(mainFab.getResId());
            }
            if (mainFab.getPressedColor()!=0){
                fab.setRippleColor(mainFab.getPressedColor());
            }
            if (mainFab.getResColor()!=0){
                fab.setColorFilter(mainFab.getResColor());
            }
            if (mainFab.getNormalColor()!=0){
                fab.setBackgroundTintList(ColorStateList.valueOf(mainFab.getNormalColor()));
            }

        }

    }

    @SuppressLint("RestrictedApi")
    private void setActionFab(){
        vertices= new Point[NUM_OF_SIDES];
        WindowManager manager= (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display= manager.getDefaultDisplay();
        Point size= new Point();
        display.getSize(size);
        int centerX= size.x/2;
        int centerY=size.y/2;
        for (int i=0;i<NUM_OF_SIDES;i++){
            vertices[i]= new Point((int)(radius*Math.cos(POSITION_CORRECTION+i*2*Math.PI/NUM_OF_SIDES))+centerX
                    ,(int)(radius*Math.sin(POSITION_CORRECTION+i*2*Math.PI/NUM_OF_SIDES))+centerY-100);
        }
        buttons= new FloatingActionButton[vertices.length];
        if (items!=null){


            for (int i=0;i<items.size();i++){
                buttons[i]= new FloatingActionButton(context);
                buttons[i].setLayoutParams(new RelativeLayout.LayoutParams(5,5));
                buttons[i].setX(0);
                buttons[i].setY(0);
                buttons[i].setOnClickListener(new ActionItemClick(items.get(i),i) );
                buttons[i].setVisibility(View.GONE);
                buttons[i].setImageResource(items.get(i).getResId());
                buttons[i].setRippleColor(items.get(i).getPressedColor());
                buttons[i].setColorFilter(items.get(i).getResColor());
                buttons[i].setTag(i);
                buttons[i].setBackgroundTintList(ColorStateList.valueOf(items.get(i).getNormalColor()));
                frameLayout.addView(buttons[i]);
            }
        }


    }



    private void playEnterAnimation(final FloatingActionButton button, int position){
        /*
          Animator that animates buttons x and y position simultaneously with size
         */
        AnimatorSet buttonAnimator = new AnimatorSet();

        /*
          ValueAnimator to update x position of a button
         */
        ValueAnimator buttonAnimatorX = ValueAnimator.ofFloat(startPositionX + button.getLayoutParams().width / 2,
                vertices[position].x);
        buttonAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setX((float) animation.getAnimatedValue() - button.getLayoutParams().width / 2);
                button.requestLayout();
            }
        });
        buttonAnimatorX.setDuration(ANIMATION_DURATION);

        /*
          ValueAnimator to update y position of a button
         */
        ValueAnimator buttonAnimatorY = ValueAnimator.ofFloat(startPositionY + 5,
                vertices[position].y);
        buttonAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setY((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorY.setDuration(ANIMATION_DURATION);

        /*
          This will increase the size of button
         */
        ValueAnimator buttonSizeAnimator = ValueAnimator.ofInt(5, width);
        buttonSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (int) animation.getAnimatedValue();
                button.getLayoutParams().height = (int) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        buttonSizeAnimator.setDuration(ANIMATION_DURATION);

        /*
          Add both x and y position update animation in
           animator set
         */
        buttonAnimator.play(buttonAnimatorX).with(buttonAnimatorY).with(buttonSizeAnimator);
        buttonAnimator.setStartDelay(enterDelay[position]);
        buttonAnimator.start();
    }
    private void playExitAnimation(final FloatingActionButton button, int position) {

        /*
          Animator that animates buttons x and y position simultaneously with size
         */
        AnimatorSet buttonAnimator = new AnimatorSet();

        /*
          ValueAnimator to update x position of a button
         */
        ValueAnimator buttonAnimatorX = ValueAnimator.ofFloat(vertices[position].x - button.getLayoutParams().width / 2,
                startPositionX);
        buttonAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setX((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorX.setDuration(ANIMATION_DURATION);

        /*
          ValueAnimator to update y position of a button
         */
        ValueAnimator buttonAnimatorY = ValueAnimator.ofFloat(vertices[position].y,
                startPositionY + 5);
        buttonAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setY((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorY.setDuration(ANIMATION_DURATION);

        /*
          This will decrease the size of button
         */
        ValueAnimator buttonSizeAnimator = ValueAnimator.ofInt(width, 5);
        buttonSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (int) animation.getAnimatedValue();
                button.getLayoutParams().height = (int) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        buttonSizeAnimator.setDuration(ANIMATION_DURATION);

        /*
          Add both x and y position update animation in
           animator set
         */
        buttonAnimator.play(buttonAnimatorX).with(buttonAnimatorY).with(buttonSizeAnimator);
        buttonAnimator.setStartDelay(exitDelay[position]);
        buttonAnimator.start();
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                button.setVisibility(GONE);
            }
        },ANIMATION_DURATION);

    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.floatingActionButton){
            rotateFoward(fab,90f);
            if (!isLayoutOpen){
                startPositionX=(int)v.getX()+50;
                startPositionY=(int)v.getY()+50;
                for (FloatingActionButton button: buttons){
                    button.setX(startPositionX);
                    button.setY(startPositionY);
                    button.setVisibility(View.VISIBLE);

                }
                for (int i=0;i<buttons.length;i++){
                    playEnterAnimation(buttons[i],i);
                }
                isLayoutOpen=true;
            }else {
                for (int i=0;i<buttons.length;i++){
                    playExitAnimation(buttons[i],i);
                    rotateFoward(fab,-90f);
                }
                isLayoutOpen=false;
            }

        }


    }

    private void rotateFoward(FloatingActionButton fab, float degrees){
        ViewCompat.animate(fab)
                .rotation(degrees)
                .withLayer()
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator(10.0f))
                .start();
    }
    @SuppressWarnings("unchecked")
    private class ActionItemClick implements View.OnClickListener{
        private final ActionItem item;
        private final int position;

        ActionItemClick(ActionItem item, int position) {
            this.item = item;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            actionFabItemClick.onActionFabItemClick ( position, item);
        }
    }
}
