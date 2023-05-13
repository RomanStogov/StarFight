package ru.myitschool.starfight;

import static ru.myitschool.starfight.MySF.SCR_HEIGHT;
import static ru.myitschool.starfight.MySF.SCR_WIDTH;

public class Sky extends SpaceObject{
    public Sky(float x, float y) {
        super(x, y, SCR_WIDTH, SCR_HEIGHT);
        vy = -1.5f;
    }
    @Override
    void move() {
        super.move();
        if(outOfBounds()){
            y = SCR_HEIGHT+height/2;
        }
    }
}
