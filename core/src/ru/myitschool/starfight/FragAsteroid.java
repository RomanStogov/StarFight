package ru.myitschool.starfight;

import com.badlogic.gdx.math.MathUtils;

public class FragAsteroid extends SpaceObject{
    int type;
    float a, v;
    float angle, speedRotation;

    public FragAsteroid(float x, float y) {
        super(x, y, MathUtils.random(30, 50), MathUtils.random(30, 50));
        a = MathUtils.random(0f, 360);
        v = MathUtils.random(1f, 8);
        vx = v*MathUtils.sin(a);
        vy = v*MathUtils.cos(a);
        type = MathUtils.random(0, 3);
        speedRotation = MathUtils.random(-5f, 5);
    }

    @Override
    void move() {
        super.move();
        angle += speedRotation;
    }
}
