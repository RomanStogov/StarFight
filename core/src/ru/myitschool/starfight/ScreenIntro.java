package ru.myitschool.starfight;

import static ru.myitschool.starfight.MySF.SCR_HEIGHT;
import static ru.myitschool.starfight.MySF.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenIntro implements Screen {
    MySF sf;
    Texture imgBackGround;
    String textTitle = "Star Fight";
    TextButton btnPlay, btnSettings, btnAbout, btnExit;


    public ScreenIntro(MySF mySF){
        sf = mySF;

        imgBackGround = new Texture("Intro.png");
        btnPlay = new TextButton(sf.fontLarge, "ИГРАТЬ", 250, 500, true);
        btnSettings = new TextButton(sf.fontLarge, "НАСТРОЙКИ", 190, 400, true);
        btnAbout = new TextButton(sf.fontLarge, "ОБ ИГРЕ", 230, 300, true);
        btnExit = new TextButton(sf.fontLarge, "ВЫХОД", 250, 200, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // касания экрана и клики мыши
        if (sf.musicOn) {
            sf.soundGame.musicIntro.setLooping(true);
            sf.soundGame.musicIntro.setVolume(0.7f);
            sf.soundGame.musicIntro.play();
        }
        if(Gdx.input.justTouched()){
            sf.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            sf.camera.unproject(sf.touch);
            if(btnPlay.hit(sf.touch.x, sf.touch.y)) {
                sf.soundGame.musicIntro.stop();
                sf.setScreen(sf.screenMap);
                if (sf.soundOn) sf.soundGame.soundClick.play();
            }
            if(btnSettings.hit(sf.touch.x, sf.touch.y)) {
                sf.soundGame.musicIntro.stop();
                sf.setScreen(sf.screenSettings);
                if (sf.soundOn) sf.soundGame.soundClick.play();
            }
            if(btnAbout.hit(sf.touch.x, sf.touch.y)) {
                sf.soundGame.musicIntro.stop();
                sf.setScreen(sf.screenAbout);
                if (sf.soundOn) sf.soundGame.soundClick.play();
            }
            if(btnExit.hit(sf.touch.x, sf.touch.y)) {
                if (sf.soundOn) sf.soundGame.soundClick.play();
                Gdx.app.exit();
            }
        }


        sf.camera.update();
        sf.batch.setProjectionMatrix(sf.camera.combined);
        sf.batch.begin();
        sf.batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        sf.fontTitle.draw(sf.batch, textTitle, SCR_WIDTH/7, 1140);
        btnPlay.font.draw(sf.batch, btnPlay.text, btnPlay.x, btnPlay.y);
        btnSettings.font.draw(sf.batch, btnSettings.text, btnSettings.x, btnSettings.y);
        btnAbout.font.draw(sf.batch, btnAbout.text, btnAbout.x, btnAbout.y);
        btnExit.font.draw(sf.batch, btnExit.text, btnExit.x, btnExit.y);
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
        sf.soundGame.soundClick.dispose();
        sf.soundGame.musicIntro.dispose();
    }
}
