package ru.myitschool.starfight;

public class Button {
    float x, y;
    float width, height;

    public Button(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    boolean hit(float tx, float ty){
        return tx>x && tx<x+width && ty>y && ty<y+height;
    }
}
