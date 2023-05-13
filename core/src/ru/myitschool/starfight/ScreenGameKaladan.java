package ru.myitschool.starfight;

import static ru.myitschool.starfight.MySF.SCR_HEIGHT;
import static ru.myitschool.starfight.MySF.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class ScreenGameKaladan implements Screen {

    Texture imgBackgrounds;

    MySF sf;
    Texture imgStars;
    Texture imgShip;
    Texture imgEnemy;
    Texture[] imgAsteroid = new Texture[2];
    Texture imgShot;
    Texture imgBack;
    Texture imgAtlasFragment;
    Texture imgAtlasFragmentAsteroid;
    Texture imgAtlasFragmentEnemy;
    TextureRegion[] imgFragment = new TextureRegion[4];
    TextureRegion[] imgFragmentAsteroid = new TextureRegion[4];
    TextureRegion[] imgFragmentEnemy = new TextureRegion[4];
    Sky[] skies = new Sky[2];

    Ship ship;
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Shot> shots = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<FragAsteroid> fragAstro = new ArrayList<>();
    ArrayList<FragEnemy> fragEnemy = new ArrayList<>();
    ArrayList<Asteroid> asteroids = new ArrayList<>();

    TextButton btnPlay, btnExit;
    Button btnBack;

    long timeEnemyLastSpawn, timeEnemySpawnInterval = 1700; // Возрождениие врагов
    long timeAsteroidLastSpawn, timeAsteroidSpawnInterval = 2200; // Возрождениие астероидов
    long timeShotLastSpawn, timeShotSpawnInterval = 500;  // Выстрелы лазерного оружия

    Player[] players = new Player[10];

    boolean pause;
    boolean gameOver;
    int frags;

    public ScreenGameKaladan(MySF mySF) {
        sf = mySF;
        imgBackgrounds = new Texture("Stars3.png");
        imgStars = new Texture("Stars.png");
        imgShip = new Texture("ship1.png");
        imgEnemy = new Texture("Enemy.png");

        for (int i = 0; i < imgAsteroid.length; i++) {
            imgAsteroid[i] = new Texture("Asteroid"+i+".png");
        }

        imgBack = new Texture("Back.png");
        imgShot = new Texture("shot.png");
        imgAtlasFragment = new Texture("Frag.png");
        imgAtlasFragmentEnemy = new Texture("Enemyfrag.png");
        imgAtlasFragmentAsteroid = new Texture("Asteroidfrag.png");
        imgFragment[0] = new TextureRegion(imgAtlasFragment, 0, 0, 200, 200);
        imgFragment[1] = new TextureRegion(imgAtlasFragment, 200, 0, 200, 200);
        imgFragment[2] = new TextureRegion(imgAtlasFragment, 0, 200, 200, 200);
        imgFragment[3] = new TextureRegion(imgAtlasFragment, 200, 200, 200, 200);
        imgFragmentAsteroid[0] = new TextureRegion(imgAtlasFragmentAsteroid, 0, 0, 200, 200);
        imgFragmentAsteroid[1] = new TextureRegion(imgAtlasFragmentAsteroid, 200, 0, 200, 200);
        imgFragmentAsteroid[2] = new TextureRegion(imgAtlasFragmentAsteroid, 0, 200, 200, 200);
        imgFragmentAsteroid[3] = new TextureRegion(imgAtlasFragmentAsteroid, 200, 200, 200, 200);
        imgFragmentEnemy[0] = new TextureRegion(imgAtlasFragmentEnemy, 0, 0, 200, 200);
        imgFragmentEnemy[1] = new TextureRegion(imgAtlasFragmentEnemy, 200, 0, 200, 200);
        imgFragmentEnemy[2] = new TextureRegion(imgAtlasFragmentEnemy, 0, 200, 200, 200);
        imgFragmentEnemy[3] = new TextureRegion(imgAtlasFragmentEnemy, 200, 200, 200, 200);

        btnPlay = new TextButton(sf.fontLarge, "ИГРАТЬ", 100, 300);
        btnExit = new TextButton(sf.fontLarge, "ВЫХОД", 400, 300);
        btnBack = new Button(320, 1180, 100, 100);

        skies[0] = new Sky(SCR_WIDTH/2, SCR_HEIGHT/2);
        skies[1] = new Sky(SCR_WIDTH/2, SCR_HEIGHT+SCR_HEIGHT/2);

        // создаём игроков
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("Noname", 0);
        }
        Player.loadTableOfRecords(players);

        sf.isAccelerometerAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        sf.isGyroscopeAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope);
        newGame();
    }

    @Override
    public void show() {
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        pause = false;
    }
    @Override
    public void render(float delta) {
        // касания экрана и клики мыши
        if(Gdx.input.isTouched()) {
            sf.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            sf.camera.unproject(sf.touch);
            ship.hit(sf.touch.x, sf.touch.y);
        }
        else if(sf.isAccelerometerAvailable && sf.AccelerometerOn) {
            ship.vx = -Gdx.input.getAccelerometerX()*10;
            ship.vy = -Gdx.input.getAccelerometerY()*15;
        }
        else if(sf.isGyroscopeAvailable && sf.GyroscopeOn) {
            ship.vx = Gdx.input.getGyroscopeY()*10;
            ship.vy = Gdx.input.getGyroscopeX()*15;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            sf.setScreen(sf.screenMap);
            if(sf.soundOn) sf.soundGame.soundClick.play();
        }
        if(Gdx.input.isTouched() && gameOver) {
            sf.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            sf.camera.unproject(sf.touch);
            if(btnPlay.hit(sf.touch.x, sf.touch.y)) {
                newGame();
            }
            if(btnExit.hit(sf.touch.x, sf.touch.y)) {
                newGame();
                sf.setScreen(sf.screenIntro);
            }
        }
        if(btnBack.hit(sf.touch.x, sf.touch.y)) {
            sf.setScreen(sf.screenMap);
        }

        //  игровые события
        if(!pause) {
            for (Sky sky : skies) sky.move();
            if(!gameOver) {
                ship.move();
            }
            if(ship.isVisible) {
                spawnShot();
            }
            spawnEnemy();
            for (int i = enemies.size()-1; i >= 0 ; i--) {
                enemies.get(i).move();
                if (enemies.get(i).outOfBounds()) {
                    if (ship.isVisible) killShip();
                    enemies.remove(i);
                    ship.hp--;
                }
            }
            spawnAsteroid();
            for (int i = asteroids.size()-1; i >= 0 ; i--) {
                asteroids.get(i).move();
                if (asteroids.get(i).outOfBounds()) {
                    asteroids.remove(i);
                }
            }

            for (int i = shots.size()-1; i >= 0; i--) {
                shots.get(i).move();
                if (shots.get(i).outOfBounds()) {
                    shots.remove(i);
                    break;
                }
                for (int j = enemies.size()-1; j >= 0; j--) {
                    if (shots.get(i).overlap(enemies.get(j))){
                        shots.remove(i);
                        enemies.get(j).hp--;

                        if (enemies.get(j).hp == 0) {
                            spawnFragEnemy(enemies.get(j).x, enemies.get(j).y);
                            enemies.remove(j);
                            frags++;
                            if (sf.soundOn) sf.soundGame.sndExplosion.play(0.6f);
                        }
                        break;
                    }
                }
            }
            for (int i = shots.size()-1; i >= 0; i--) {
                shots.get(i).move();
                if (shots.get(i).outOfBounds()) {
                    shots.remove(i);
                    break;
                }
                for (int j = asteroids.size()-1; j >= 0; j--) {
                    if (shots.get(i).overlap(asteroids.get(j))){
                        shots.remove(i);
                        asteroids.get(j).hp--;

                        if (asteroids.get(j).hp == 0){
                            spawnFragAsteroid(asteroids.get(j).x, asteroids.get(j).y);
                            asteroids.remove(j);
                            if (sf.soundOn) sf.soundGame.sndExplosion.play(0.6f);
                        }
                        break;
                    }
                }
            }
        }
        for (int i = fragments.size()-1; i >= 0 ; i--) {
            fragments.get(i).move();
            if (fragments.get(i).outOfBounds()) {
                fragments.remove(i);
            }
        }
        for (int i = fragAstro.size()-1; i >= 0 ; i--) {
            fragAstro.get(i).move();
            if (fragAstro.get(i).outOfBounds()) {
                fragAstro.remove(i);
            }
        }
        for (int i = fragEnemy.size()-1; i >= 0 ; i--) {
            fragEnemy.get(i).move();
            if (fragEnemy.get(i).outOfBounds()) {
                fragEnemy.remove(i);
            }
        }

        // отрисовка всего
        sf.camera.update();
        sf.batch.setProjectionMatrix(sf.camera.combined);
        sf.batch.begin();
        sf.batch.draw(imgBackgrounds, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for(Sky sky: skies) sf.batch.draw(imgStars, sky.getX(), sky.getY(), sky.width, sky.height);
        for(Fragment frag: fragments)
            sf.batch.draw(imgFragment[frag.type], frag.getX(), frag.getY(), frag.width/2, frag.height/2,
                    frag.width, frag.height, 1, 1, frag.angle);
        for(FragEnemy fragEnemy: fragEnemy)
            sf.batch.draw(imgFragmentEnemy[fragEnemy.type], fragEnemy.getX(), fragEnemy.getY(), fragEnemy.width/2, fragEnemy.height/2,
                    fragEnemy.width, fragEnemy.height, 1, 1, fragEnemy.angle);
        for(FragAsteroid fragAsteroid: fragAstro)
            sf.batch.draw(imgFragmentAsteroid[fragAsteroid.type], fragAsteroid.getX(), fragAsteroid.getY(), fragAsteroid.width/2, fragAsteroid.height/2,
                    fragAsteroid.width, fragAsteroid.height, 1, 1, fragAsteroid.angle);
        for(Asteroid asteroid: asteroids)
            sf.batch.draw(imgAsteroid[asteroid.type], asteroid.getX(), asteroid.getY(), asteroid.width/2, asteroid.height/2,
                    asteroid.width, asteroid.height, 1, 1, asteroid.speedrotation, 1, 1, 420, 420, false, false);
        for(Enemy enemy: enemies) sf.batch.draw(imgEnemy, enemy.getX(), enemy.getY(), enemy.width, enemy.height);

        for(Shot shot: shots) sf.batch.draw(imgShot, shot.getX(), shot.getY(), shot.width, shot.height);
        if(ship.isVisible) sf.batch.draw(imgShip, ship.getX(), ship.getY(), ship.width, ship.height);
        sf.font.draw(sf.batch, "Очков: "+frags, 10, SCR_HEIGHT-10);
        for (int i = 1; i < ship.lives+1; i++) {
            sf.batch.draw(imgShip, SCR_WIDTH-60*i, SCR_HEIGHT-60, 50, 50);
        }
        if(gameOver) {
            sf.font.draw(sf.batch, Player.tableOfRecordsToString(players), 200, SCR_HEIGHT-200);
            btnPlay.font.draw(sf.batch, btnPlay.text, btnPlay.x, btnPlay.y);
            btnExit.font.draw(sf.batch, btnExit.text, btnExit.x, btnExit.y);
        }
        sf.batch.draw(imgBack, btnBack.x, btnBack.y, btnBack.width, btnBack.height);
        sf.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setCatchKey(Input.Keys.BACK, false);
        pause = true;
    }

    @Override
    public void dispose() {
        imgStars.dispose();
        imgShip.dispose();
        imgEnemy.dispose();
        for (Texture texture : imgAsteroid) {
            texture.dispose();
        }
        imgBack.dispose();
        imgAtlasFragment.dispose();
        imgAtlasFragmentAsteroid.dispose();
        imgAtlasFragmentEnemy.dispose();
        sf.soundGame.sndExplosion.dispose();
        sf.soundGame.sndShot.dispose();
    }

    void spawnEnemy(){
        if(TimeUtils.millis() > timeEnemyLastSpawn+timeEnemySpawnInterval) {
            enemies.add(new Enemy());
            timeEnemyLastSpawn = TimeUtils.millis();
        }
    }

    void spawnAsteroid(){
        if(TimeUtils.millis() > timeAsteroidLastSpawn+timeAsteroidSpawnInterval) {
            asteroids.add(new Asteroid());
            timeAsteroidLastSpawn = TimeUtils.millis();
        }
    }

    void spawnShot(){
        if(TimeUtils.millis() > timeShotLastSpawn+timeShotSpawnInterval) {
            shots.add(new Shot(ship.x, ship.y));
            timeShotLastSpawn = TimeUtils.millis();
            if(sf.soundOn) sf.soundGame.sndShot.play();
        }
    }

    void spawnFragments(float x, float y){
        for (int i = 0; i < 10; i++) {
            fragments.add(new Fragment(x, y));
        }
    }
    void spawnFragEnemy(float x, float y){
        for (int i = 0; i < 10; i++) {
            fragEnemy.add(new FragEnemy(x, y));
        }
    }
    void spawnFragAsteroid(float x, float y){
        for (int i = 0; i < 10; i++) {
            fragAstro.add(new FragAsteroid(x, y));
        }
    }

    void killShip(){
        spawnFragments(ship.x, ship.y);
        ship.kill();
        if(sf.soundOn) sf.soundGame.sndExplosion.play();
        if(ship.lives == 0) gameOver();
    }

    void newGame(){
        ship = new Ship(SCR_WIDTH/2, 300, 100, 100);
        enemies.clear();
        fragments.clear();
        fragAstro.clear();
        fragEnemy.clear();
        shots.clear();
        asteroids.clear();
        gameOver = false;
        frags = 0;
    }

    void gameOver(){
        gameOver = true;
        players[players.length-1].name = sf.playerName;
        players[players.length-1].frags = frags;
        Player.sortTableOfRecords(players);
        Player.saveTableOfRecords(players);
    }
}
