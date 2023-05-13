package ru.myitschool.starfight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

public class MySF extends Game {
	public static final float SCR_WIDTH = 720, SCR_HEIGHT = 1280;
	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touch;
	BitmapFont font, fontLarge, fontSetting, fontTitle, fontPlanet;

	ScreenIntro screenIntro;
	ScreenMap screenMap;
	ScreenGamePolifem screenGamePolifem;
	ScreenGameArracis screenGameArracis;
	ScreenGameKaladan screenGameKaladan;
	ScreenSettings screenSettings;
	ScreenAbout screenAbout;
	SoundGame soundGame;

	boolean soundOn = true;
	boolean musicOn = true;

	boolean isGyroscopeAvailable;
	boolean isAccelerometerAvailable;

	boolean GyroscopeOn;
	boolean AccelerometerOn;

	String playerName = "Noname";

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		touch = new Vector3();
		generateFont();

		screenIntro = new ScreenIntro(this);
		screenMap = new ScreenMap(this);
		screenGamePolifem = new ScreenGamePolifem(this);
		screenGameArracis = new ScreenGameArracis(this);
		screenGameKaladan = new ScreenGameKaladan (this);
		screenSettings = new ScreenSettings(this);
		screenAbout = new ScreenAbout(this);
		soundGame = new SoundGame(this);
		setScreen(screenIntro);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	void generateFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("RuinedC.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 60;
		parameter.color = new Color().set(1, 0.9f, 0.4f, 1);
		parameter.borderColor = new Color().set(0, 0, 0, 1);
		parameter.borderWidth = 2;
		parameter.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
		font = generator.generateFont(parameter);
		parameter.color = new Color().set(1, 0.9f, 0.4f, 1);
		parameter.size = 80;
		fontLarge = generator.generateFont(parameter);
		parameter.color = new Color().set(1, 0.9f, 0.4f, 1);
		parameter.size = 70;
		fontSetting = generator.generateFont(parameter);
		parameter.color = new Color().set(1, 0.8f, 0.4f, 1);
		parameter.size = 110;
		fontTitle = generator.generateFont(parameter);
		parameter.color = new Color().set(1, 1, 1, 1);
		parameter.size = 50;
		fontPlanet = generator.generateFont(parameter);
		generator.dispose();
	}
}
