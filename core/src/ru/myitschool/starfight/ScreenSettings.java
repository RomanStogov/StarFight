package ru.myitschool.starfight;

import static ru.myitschool.starfight.MySF.SCR_HEIGHT;
import static ru.myitschool.starfight.MySF.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenSettings implements Screen {
    MySF sf;
    Texture imgBackGround;

    TextButton btnName, btnClearRec, btnSound, btnMusic, btnBack, btnAccelerometer, btnGyroscope;

    InputKeyboard keyboard;
    boolean isEnterName;

    public ScreenSettings(MySF mySF){
        sf = mySF;
        imgBackGround = new Texture("Setting.png");
        btnName = new TextButton(sf.fontSetting, "Имя: "+sf.playerName, 20, 1100, true);
        btnClearRec = new TextButton(sf.fontSetting, "Очистка рекордов", 20, 1000, true);
        btnSound = new TextButton(sf.fontSetting, "Звук вкл", 20, 900, true);
        btnMusic = new TextButton(sf.fontSetting, "Музыка вкл", 20, 800, true);
        btnAccelerometer = new TextButton(sf.fontSetting, "Акселерометр вкл", 20, 700, true);
        btnGyroscope = new TextButton(sf.fontSetting, "Гироскоп вкл", 20, 600, true);
        btnBack = new TextButton(sf.fontSetting, "Назад", 20, 500, true);
        keyboard = new InputKeyboard(SCR_WIDTH, SCR_HEIGHT/1.7f, 8);
        loadSettings();
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
            if(isEnterName){
                if(keyboard.endOfEdit(sf.touch.x, sf.touch.y)){
                    sf.playerName = keyboard.getText();
                    btnName.setText("Имя: "+sf.playerName);
                    isEnterName = false;
                }
            } else {
                if (btnName.hit(sf.touch.x, sf.touch.y)) {
                    isEnterName = true;
                    if (sf.soundOn) sf.soundGame.soundClick.play();
                }
                if (btnClearRec.hit(sf.touch.x, sf.touch.y)) {
                    Player.clearTableOfRecords(sf.screenGamePolifem.players);
                    Player.clearTableOfRecords(sf.screenGameArracis.players);
                    Player.clearTableOfRecords(sf.screenGameKaladan.players);
                    Player.saveTableOfRecords(sf.screenGameArracis.players);
                    Player.saveTableOfRecords(sf.screenGameKaladan.players);
                    Player.saveTableOfRecords(sf.screenGamePolifem.players);
                    btnClearRec.setText("Рекорды очищены");
                    if (sf.soundOn) sf.soundGame.soundClick.play();
                }
                if (btnSound.hit(sf.touch.x, sf.touch.y)) {
                    sf.soundOn = !sf.soundOn;
                    btnSound.setText(sf.soundOn ? "Звук вкл" : "Звук выкл");
                    if (sf.soundOn) sf.soundGame.soundClick.play();
                }
                if (btnMusic.hit(sf.touch.x, sf.touch.y)) {
                    sf.musicOn = !sf.musicOn;
                    btnMusic.setText(sf.musicOn ? "Музыка вкл" : "Музыка выкл");
                    if (sf.soundOn) sf.soundGame.soundClick.play();
                }
                if (btnBack.hit(sf.touch.x, sf.touch.y)) {
                    if (sf.soundOn) sf.soundGame.soundClick.play();
                    sf.setScreen(sf.screenIntro);
                }
                if (btnAccelerometer.hit(sf.touch.x, sf.touch.y)){
                    sf.AccelerometerOn = !sf.AccelerometerOn;
                    btnAccelerometer.setText(sf.AccelerometerOn ? "Акселерометр вкл" : "Акселерометр выкл");
                    if (sf.soundOn) sf.soundGame.soundClick.play();
                }
                if (btnGyroscope.hit(sf.touch.x, sf.touch.y)) {
                    sf.GyroscopeOn = !sf.GyroscopeOn;
                    btnGyroscope.setText(sf.GyroscopeOn ? "Гироскоп вкл" : "Гироскоп выкл");
                    if (sf.soundOn) sf.soundGame.soundClick.play();
                }
            }
        }

        sf.camera.update();
        sf.batch.setProjectionMatrix(sf.camera.combined);
        sf.batch.begin();
        sf.batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        btnName.font.draw(sf.batch, btnName.text, btnName.x, btnName.y);
        btnClearRec.font.draw(sf.batch, btnClearRec.text, btnClearRec.x, btnClearRec.y);
        btnSound.font.draw(sf.batch, btnSound.text, btnSound.x, btnSound.y);
        btnMusic.font.draw(sf.batch, btnMusic.text, btnMusic.x, btnMusic.y);
        btnAccelerometer.font.draw(sf.batch, btnAccelerometer.text, btnAccelerometer.x, btnAccelerometer.y);
        btnGyroscope.font.draw(sf.batch, btnGyroscope.text, btnGyroscope.x, btnGyroscope.y);
        btnBack.font.draw(sf.batch, btnBack.text, btnBack.x, btnBack.y);
        if(isEnterName) keyboard.draw(sf.batch);
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
        btnClearRec.setText("Очистка рекордов");
        saveSettings();
    }

    @Override
    public void dispose() {
        imgBackGround.dispose();
        keyboard.dispose();
        sf.soundGame.soundClick.dispose();
    }
    void saveSettings() {
        Preferences pref = Gdx.app.getPreferences("settings");
        pref.putString("namePlayer", sf.playerName);
        pref.putBoolean("sound", sf.soundOn);
        pref.putBoolean("music", sf.musicOn);
        pref.putBoolean("accelerometer", sf.AccelerometerOn);
        pref.putBoolean("gyroscope", sf.GyroscopeOn);
        pref.flush();
    }

    void loadSettings() {
        Preferences pref = Gdx.app.getPreferences("settings");
        if(pref.contains("namePlayer")) sf.playerName = pref.getString("namePlayer");
        if(pref.contains("sound")) sf.soundOn = pref.getBoolean("sound");
        if(pref.contains("music")) sf.musicOn = pref.getBoolean("music");
        if(pref.contains("accelerometer")) sf.AccelerometerOn = pref.getBoolean("accelerometer");
        if(pref.contains("gyroscope")) sf.GyroscopeOn = pref.getBoolean("gyroscope");
        buttonsUpdate();
    }

    void buttonsUpdate() {
        btnName.setText("Имя: "+sf.playerName);
        btnSound.setText(sf.soundOn ? "Звук вкл" : "Звук выкл");
        btnMusic.setText(sf.musicOn ? "Музыка вкл" : "Музыка выкл");
        btnAccelerometer.setText(sf.AccelerometerOn ? "Акселерометр вкл" : "Акселерометр выкл");
        btnGyroscope.setText(sf.GyroscopeOn ? "Гироскоп вкл" : "Гироскоп выкл");
    }
}


