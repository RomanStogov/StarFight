package ru.myitschool.starfight;

import static ru.myitschool.starfight.MySF.SCR_HEIGHT;
import static ru.myitschool.starfight.MySF.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenAbout implements Screen {
    MySF sf;
    Texture imgBackGround;

    TextButton btnBack;
    String textAbout =  "Эта игра - космическая\n" +
            "стрелялка создана в\n" +
            "IT-школе Samsung на\n" +
            " Java под Android c\n" +
            "использованием LibGdx\n\n" +
            "Цель игры - сбивать\n" +
            "вражеские космические\n" +
            "корабли на трех \n" +
            "уровнях и получать\n" +
            "очки.\n";

    public ScreenAbout(MySF mySF){
        sf = mySF;
        imgBackGround = new Texture("About.png");

        btnBack = new TextButton(sf.fontLarge, "Назад", 100, 200, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // касания экрана и клики мыши
        if(Gdx.input.justTouched()){
            sf.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            sf.camera.unproject(sf.touch);

            if(btnBack.hit(sf.touch.x, sf.touch.y)) {
                sf.setScreen(sf.screenIntro);
                if(sf.soundOn) sf.soundGame.soundClick.play();
            }
        }

        sf.camera.update();
        sf.batch.setProjectionMatrix(sf.camera.combined);
        sf.batch.begin();
        sf.batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        sf.font.draw(sf.batch, textAbout, 20, 1100);
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
        sf.soundGame.soundClick.dispose();
    }
}
