package ru.myitschool.starfight;

import static ru.myitschool.starfight.MySF.*;

import com.badlogic.gdx.math.MathUtils;

public class Enemy extends SpaceObject{
    int type;
    public Enemy() {
        super(0, 0, 100, 100);
        x = MathUtils.random(width/2, SCR_WIDTH-width/2);
        y = MathUtils.random(SCR_HEIGHT, SCR_HEIGHT+height/2);
        vy = MathUtils.random(-3f, -4);
        hp = 2;

        type = MathUtils.random(0, 1);
    }

    @Override
    boolean outOfBounds() {
        return y<-height/2;
    }
}
