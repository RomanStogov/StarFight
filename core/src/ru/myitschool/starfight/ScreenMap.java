package ru.myitschool.starfight;

import static ru.myitschool.starfight.MySF.SCR_HEIGHT;
import static ru.myitschool.starfight.MySF.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenMap implements Screen {
    MySF sf;
    Texture imgBackGround;
    Texture imgPlanet1;
    Texture imgPlanet2;
    Texture imgPlanet3;

    TextButton  btnLevel01, btnLevel02, btnLevel03, btnBack;
    Button btnPlanet1, btnPlanet2, btnPlanet3;

    public ScreenMap(MySF mySF){
        sf = mySF;
        imgBackGround = new Texture("Map.png");
        imgPlanet1 = new Texture("polifem.png");
        imgPlanet2 = new Texture("arracis.png");
        imgPlanet3 = new Texture("kaladan.png");
        btnPlanet1 = new Button(115, 300,210, 210);
        btnPlanet2 = new Button(345, 560,190, 190);
        btnPlanet3 = new Button(420, 920,180, 180);
        btnLevel01 = new TextButton(sf.fontPlanet, "Полифем", 120, 300);
        btnLevel02 = new TextButton(sf.fontPlanet, "Арракис", 350, 560);
        btnLevel03 = new TextButton(sf.fontPlanet, "Каладан", 420, 920);
        btnBack = new TextButton(sf.font, "Назад", 50, 100);
    }

    @Override
    public void show() {
    }
    @Override
    public void render(float delta) {
        // касания экрана и клики мыши
        if (sf.musicOn) {
            sf.soundGame.musicBackground.setLooping(true);
            sf.soundGame.musicBackground.setVolume(0.6f);
            sf.soundGame.musicBackground.play();
        }
        if(Gdx.input.justTouched()){
            sf.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            sf.camera.unproject(sf.touch);
            if(btnLevel01.hit(sf.touch.x, sf.touch.y)){
                sf.soundGame.musicBackground.stop();
                if (sf.soundOn) sf.soundGame.soundClick.play();
                sf.setScreen(sf.screenGamePolifem);
            }
            if(btnLevel02.hit(sf.touch.x, sf.touch.y)) {
                sf.soundGame.musicBackground.stop();
                if (sf.soundOn) sf.soundGame.soundClick.play();
                sf.setScreen(sf.screenGameArracis);
            }
            if(btnLevel03.hit(sf.touch.x, sf.touch.y)) {
                sf.soundGame.musicBackground.stop();
                if (sf.soundOn) sf.soundGame.soundClick.play();
                sf.setScreen(sf.screenGameKaladan);
            }
            if(btnBack.hit(sf.touch.x, sf.touch.y)) {
                sf.soundGame.musicBackground.stop();
                if (sf.soundOn) sf.soundGame.soundClick.play();
                sf.setScreen(sf.screenIntro);
            }
            if(btnPlanet1.hit(sf.touch.x, sf.touch.y)) {
                sf.soundGame.musicBackground.stop();
                if (sf.soundOn) sf.soundGame.soundClick.play();
                sf.setScreen(sf.screenGamePolifem);
            }
            if(btnPlanet2.hit(sf.touch.x, sf.touch.y)) {
                sf.soundGame.musicBackground.stop();
                if (sf.soundOn) sf.soundGame.soundClick.play();
                sf.setScreen(sf.screenGameArracis);
            }
            if(btnPlanet3.hit(sf.touch.x, sf.touch.y)) {
                sf.soundGame.musicBackground.stop();
                if (sf.soundOn) sf.soundGame.soundClick.play();
                sf.setScreen(sf.screenGameKaladan);
            }
        }

        sf.camera.update();
        sf.batch.setProjectionMatrix(sf.camera.combined);
        sf.batch.begin();
        sf.batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        sf.batch.draw(imgPlanet1, btnPlanet1.x, btnPlanet1.y, btnPlanet1.width, btnPlanet1.height);
        sf.batch.draw(imgPlanet2, btnPlanet2.x, btnPlanet2.y, btnPlanet2.width, btnPlanet2.height);
        sf.batch.draw(imgPlanet3, btnPlanet3.x, btnPlanet3.y, btnPlanet3.width, btnPlanet3.height);
        btnLevel01.font.draw(sf.batch, btnLevel01.text, btnLevel01.x, btnLevel01.y);
        btnLevel02.font.draw(sf.batch, btnLevel02.text, btnLevel02.x, btnLevel02.y);
        btnLevel03.font.draw(sf.batch, btnLevel03.text, btnLevel03.x, btnLevel03.y);
        btnBack.font.draw(sf.batch, btnBack.text, btnBack.x, btnBack.y);

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

    }

    @Override
    public void dispose() {
        imgBackGround.dispose();
        imgPlanet1.dispose();
        imgPlanet2.dispose();
        imgPlanet3.dispose();
        sf.soundGame.soundClick.dispose();
        sf.soundGame.musicBackground.dispose();
    }
}
