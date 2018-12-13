package com.revosleap.actionfab.layout;

import java.io.Serializable;

public class ActionItems<T> implements Serializable {
    private int resId;
    private int resColor;
    private int pressedColor;
    private int normalColor;
    private T wrapper;

    public ActionItems() {
    }

    public int getResId() {
        return resId;
    }
    public ActionItems<T> setResId(int resId){
        this.resId=resId;
        return this;
    }

    public int getResColor() {
        return resColor;
    }
    public ActionItems<T> setResColor(int resColor){
        this.resColor=resColor;
        return this;
    }

    public int getPressedColor() {
        return pressedColor;
    }
    public ActionItems<T> setPressedColor(int pressedColor){
        this.pressedColor=pressedColor;
        return this;
    }

    public int getNormalColor() {
        return normalColor;
    }
    public ActionItems<T> setNormalColor(int normalColor){
        this.normalColor=normalColor;
        return this;
    }

}
