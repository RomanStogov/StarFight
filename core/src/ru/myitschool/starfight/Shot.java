package ru.myitschool.starfight;

public class Shot extends SpaceObject{
    public Shot(float x, float y) {
        super(x, y, 100, 100);
        vy = 20;
    }

    boolean overlap(Enemy enemy){
        return Math.abs(x-enemy.x) < width/2 + enemy.width/2 &&
                Math.abs(y-enemy.y) < height/3 + enemy.height/3;
    }
    boolean overlap(Asteroid enemy){
        return Math.abs(x-enemy.x) < width/2 + enemy.width/2 &&
                Math.abs(y-enemy.y) < height/3 + enemy.height/3;
    }
}
