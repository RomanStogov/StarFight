package ru.myitschool.starfight;

import static ru.myitschool.starfight.MySF.SCR_HEIGHT;
import static ru.myitschool.starfight.MySF.SCR_WIDTH;

public class SpaceObject {
    float x, y;
    float width, height;
    float vx, vy;
    int hp; // здоровье


    public SpaceObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void move() {
        x += vx;
        y += vy;
    }

    public float getX() {
        return x-width/2;
    }

    public float getY() {
        return y-height/2;
    }

    boolean outOfBounds(){
        return x<-width/2 || x>SCR_WIDTH+width/2 || y<-height/2 || y>SCR_HEIGHT+height/2;
    }
}

