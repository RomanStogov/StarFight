package ru.myitschool.starfight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class SoundGame {
    MySF sf;
    Sound soundClick, sndShot, sndExplosion;
    Music musicBackground, musicIntro;

    public SoundGame(MySF mySF){
        sf = mySF;
        soundClick = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
        musicBackground = Gdx.audio.newMusic(Gdx.files.internal("Amb.mp3"));
        sndShot = Gdx.audio.newSound(Gdx.files.internal("blaster.mp3"));
        sndExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"));
        musicIntro= Gdx.audio.newMusic(Gdx.files.internal("Intro.mp3"));
    }
}
