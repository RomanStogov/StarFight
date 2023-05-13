package ru.myitschool.starfight;

import static ru.myitschool.starfight.MySF.SCR_HEIGHT;
import static ru.myitschool.starfight.MySF.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class Asteroid extends SpaceObject{

    float speedrotation, speedRotation;
    int type;

    public Asteroid() {
        super(0, 0, 100, 100);
        x = MathUtils.random(width/2, SCR_WIDTH-width/2);
        y = MathUtils.random(SCR_HEIGHT, SCR_HEIGHT+height/2);
        vy = MathUtils.random(-2f, -4);
        hp = 3;
        speedRotation = MathUtils.random(-2f, 2);
        type = MathUtils.random(0, 1);

    }

    @Override
    boolean outOfBounds() {
        return y<-height/2;
    }

    @Override
    void move() {
        super.move();
        speedrotation += speedRotation;
    }
}
