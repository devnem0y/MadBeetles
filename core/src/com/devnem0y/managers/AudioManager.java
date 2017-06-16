package com.devnem0y.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

//    private Music[] playList;
//    private Music track1, track2, track3, track4, track5;
//    private int currentPlaying;
//    private Random random = new Random();

    private Music music, moveM, signalWinM, gameOverM;
    private Sound click, shotgun, ammo, bit, damage, killAll, medic;
    private static Preferences configMusic, configSounds;

    public AudioManager() {
//        loading(app);
//        playList = new Music[] {track1, track2, track3, track4, track5};
//        currentPlaying = random.nextInt(playList.length);

        resourseLoader();

        configSounds = Gdx.app.getPreferences("confSounds");
        if (!configSounds.contains("souVol")) {
            configSounds.putBoolean("souVol", true);
        }
        settingsSound();

        configMusic = Gdx.app.getPreferences("confMusic");
        if (!configMusic.contains("musVol")) {
            configMusic.putBoolean("musVol", true);
        }
        settingsMusic();
    }

    public void settingsMusic() {
        float volumeM;
        if (getMusicVolume()) volumeM = 0.5f;
        else volumeM = 0f;
        music.setVolume(volumeM);
        moveM.setVolume(settingsSound());
        signalWinM.setVolume(settingsSound());
        gameOverM.setVolume(settingsSound());
    }

    public float settingsSound() {
        float volumeS;
        if (getSoundsVolume()) volumeS = 1f;
        else volumeS = 0f;
        return volumeS;
    }

    private void resourseLoader() {
        music = Gdx.audio.newMusic(Gdx.files.internal("music/track_01.mp3"));
        moveM = Gdx.audio.newMusic(Gdx.files.internal("sounds/moveM.mp3"));
        signalWinM = Gdx.audio.newMusic(Gdx.files.internal("sounds/signalWin.mp3"));
        gameOverM = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameOver.mp3"));

        click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
        shotgun = Gdx.audio.newSound(Gdx.files.internal("sounds/shotgun.wav"));
        ammo = Gdx.audio.newSound(Gdx.files.internal("sounds/ammo.wav"));
        bit = Gdx.audio.newSound(Gdx.files.internal("sounds/bit.wav"));
        medic = Gdx.audio.newSound(Gdx.files.internal("sounds/medic.wav"));
        killAll = Gdx.audio.newSound(Gdx.files.internal("sounds/killAll.wav"));
        damage = Gdx.audio.newSound(Gdx.files.internal("sounds/damage.wav"));

    }

//    public void update(float val) {
//        if (!playList[currentPlaying].isPlaying()) {
//            currentPlaying = random.nextInt(playList.length);
//            playList[currentPlaying].setVolume(val);
//            isPlayingTrack(true);
//        }
//    }
//
//    public void isPlayingTrack(boolean isPlaying) {
//        if (isPlaying) playList[currentPlaying].play();
//        else playList[currentPlaying].stop();
//    }
//
//    private void loading(Application app) {
//        track1 = app.assetManager.get("music/track_01.mp3", Music.class);
//        track2 = app.assetManager.get("music/track_02.mp3", Music.class);
//        track3 = app.assetManager.get("music/track_03.mp3", Music.class);
//        track4 = app.assetManager.get("music/track_04.mp3", Music.class);
//        track5 = app.assetManager.get("music/track_05.mp3", Music.class);
//    }

    public static void setMusicVolume(boolean val) {
        configMusic.putBoolean("musVol", val);
        configMusic.flush();
    }

    public static boolean getMusicVolume() {
        return configMusic.getBoolean("musVol");
    }

    public static void setSoundsVolume(boolean val) {
        configSounds.putBoolean("souVol", val);
        configSounds.flush();
    }

    public static boolean getSoundsVolume() {
        return configSounds.getBoolean("souVol");
    }

    public Music getMusic() {
        return music;
    }

    public Sound getClick() {
        return click;
    }

    public Music getMoveM() {
        return moveM;
    }

    public Music getSignalWinM() {
        return signalWinM;
    }

    public Music getGameOverM() {
        return gameOverM;
    }

    public Sound getShotgun() {
        return shotgun;
    }

    public Sound getAmmo() {
        return ammo;
    }

    public Sound getBit() {
        return bit;
    }

    public Sound getDamage() {
        return damage;
    }

    public Sound getKillAll() {
        return killAll;
    }

    public Sound getMedic() {
        return medic;
    }

    public void dispose() {
//        track1.dispose();
//        track2.dispose();
//        track3.dispose();
//        track4.dispose();
//        track5.dispose();

        music.dispose();
        moveM.dispose();
        signalWinM.dispose();
        gameOverM.dispose();
        click.dispose();
        shotgun.dispose();
        ammo.dispose();
        bit.dispose();
        medic.dispose();
        killAll.dispose();
        damage.dispose();
    }
}