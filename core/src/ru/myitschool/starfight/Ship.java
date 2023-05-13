package ru.myitschool.starfight;

import static ru.myitschool.starfight.MySF.SCR_WIDTH;
import static ru.myitschool.starfight.MySF.SCR_HEIGHT;

import com.badlogic.gdx.utils.TimeUtils;

public class Ship extends SpaceObject{
    boolean isVisible = true;
    int lives = 3;
    long timeStartInvisible, timeInvisibleInterval = 1000;

    public Ship(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    void move() {
        super.move();
        outOfBounds();
        if(!isVisible) {
            if(timeStartInvisible+timeInvisibleInterval<TimeUtils.millis()){
                isVisible = true;
                x = SCR_WIDTH/2;
                y = SCR_HEIGHT/2;
            }
        }
    }

    void hit(float tx, float ty) {
        vx = (tx-x)/10;
        vy = (ty-y)/10;
    }

    @Override
    boolean outOfBounds() {
        if(x < width/2) {
            x = width/2;
            vx = 0;
        }
        if(y < height/1.5f) {
            y = height/1.5f;
            vy = 0;
        }

        if(x > SCR_WIDTH-width/2){
            x = SCR_WIDTH-width/2;
            vx = 0;
        }
        if(y > SCR_HEIGHT/2-height/2){
            y = SCR_HEIGHT/2-height/2;
            vy = 0;
        }
        return true;
    }

    void kill(){
        isVisible = false;
        timeStartInvisible = TimeUtils.millis();
        lives--;
    }
}
