package com.revosleap.actionfab;

import java.io.Serializable;

public class ActionItem<T> implements Serializable {
    private int resId;
    private int resColor;
    private int pressedColor;
    private int normalColor;
    private T wrapper;

    public ActionItem() {
    }

    public int getResId() {
        return resId;
    }
    public ActionItem<T> setResId(int resId){
        this.resId=resId;
        return this;
    }

    public int getResColor() {
        return resColor;
    }
    public ActionItem<T> setResColor(int resColor){
        this.resColor=resColor;
        return this;
    }

    public int getPressedColor() {
        return pressedColor;
    }
    public ActionItem<T> setPressedColor(int pressedColor){
        this.pressedColor=pressedColor;
        return this;
    }

    public int getNormalColor() {
        return normalColor;
    }
    public ActionItem<T> setNormalColor(int normalColor){
        this.normalColor=normalColor;
        return this;
    }

}
