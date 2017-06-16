package com.devnem0y.handlers.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.devnem0y.handlers.GameLogic;
import com.devnem0y.screens.GameScreen;

import static com.devnem0y.utils.Constants.*;

public class ButtonsMenu {

    private Button play, restart, next, exit, soundOn, soundOff, musicOn, musicOff;
    private Button.ButtonStyle playStyle, restartStyle, nextStyle, exitStyle, soundOnStyle, soundOffStyle, musicOnStyle, musicOffStyle;
    public Group mainMenuGroup, restartGroup, nextGroup;
    private Skin skin, skinSound;

    public ButtonsMenu() {
        skin = new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("image/buttons_menu.atlas")));
        skinSound = new Skin();
        skinSound.addRegions(new TextureAtlas(Gdx.files.internal("image/soundBtn.atlas")));
    }

    public void createMenu(Stage stage) {
        initMenu(stage);
        initRestart(stage);
        initNext(stage);
    }

    private void initMenu(Stage stage) {
        mainMenuGroup = new Group();
        mainMenuGroup.setPosition(0, APP_HEIGHT + 300);

        playStyle = new Button.ButtonStyle();
        playStyle.down = skin.getDrawable("playDown");
        playStyle.up = skin.getDrawable("playUp");

        exitStyle = new Button.ButtonStyle();
        exitStyle.down = skin.getDrawable("exitDown");
        exitStyle.up = skin.getDrawable("exitUp");

        //============================================

        soundOnStyle = new Button.ButtonStyle();
        soundOnStyle.down = skinSound.getDrawable("soundOffDown");
        soundOnStyle.up = skinSound.getDrawable("soundOnUp");

        soundOffStyle = new Button.ButtonStyle();
        soundOffStyle.down = skinSound.getDrawable("soundOnDown");
        soundOffStyle.up = skinSound.getDrawable("soundOffUp");

        musicOnStyle = new Button.ButtonStyle();
        musicOnStyle.down = skinSound.getDrawable("musicOffDown");
        musicOnStyle.up = skinSound.getDrawable("musicOnUp");

        musicOffStyle = new Button.ButtonStyle();
        musicOffStyle.down = skinSound.getDrawable("musicOnDown");
        musicOffStyle.up = skinSound.getDrawable("musicOffUp");

        play = new Button(playStyle);
        play.setPosition(APP_WIDTH / 2 - 85, 180);
        play.setSize(170, 170);
        play.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("PLAY: 1");
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("PLAY: 0");
                GameLogic.audioManager.getClick().play(GameLogic.audioManager.settingsSound());
                GameScreen.gameState = GameScreen.GameState.START;
            }
        });

        exit = new Button(exitStyle);
        exit.setPosition(APP_WIDTH - 70, APP_HEIGHT - 70);
        exit.setSize(60, 60);
        exit.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 1");
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 0");
                GameLogic.audioManager.getClick().play(GameLogic.audioManager.settingsSound());
                Gdx.app.exit();
            }
        });

        //============================================================

        soundOn = new Button(soundOnStyle);
        if (GameLogic.audioManager.getSoundsVolume()) soundOn.setPosition(10, APP_HEIGHT - 70);
        else soundOn.setPosition(-100, APP_HEIGHT - 70);
        soundOn.setSize(60, 60);
        soundOn.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 1");
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 0");
                soundOn.setPosition(-100, APP_HEIGHT - 70);
                soundOff.setPosition(10, APP_HEIGHT - 70);
                GameLogic.audioManager.getClick().play(GameLogic.audioManager.settingsSound());
                GameLogic.audioManager.setSoundsVolume(false);
            }
        });

        soundOff = new Button(soundOffStyle);
        if (!GameLogic.audioManager.getSoundsVolume()) soundOff.setPosition(10, APP_HEIGHT - 70);
        else soundOff.setPosition(-100, APP_HEIGHT - 70);
        soundOff.setSize(60, 60);
        soundOff.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 1");
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 0");
                soundOff.setPosition(-100, APP_HEIGHT - 70);
                soundOn.setPosition(10, APP_HEIGHT - 70);
                GameLogic.audioManager.getClick().play(GameLogic.audioManager.settingsSound());
                GameLogic.audioManager.setSoundsVolume(true);
            }
        });

        musicOn = new Button(musicOnStyle);
        if (GameLogic.audioManager.getMusicVolume()) musicOn.setPosition(10, APP_HEIGHT - 140);
        else musicOn.setPosition(-100, APP_HEIGHT - 140);
        musicOn.setSize(60, 60);
        musicOn.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 1");
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 0");
                musicOn.setPosition(-100, APP_HEIGHT - 140);
                musicOff.setPosition(10, APP_HEIGHT - 140);
                GameLogic.audioManager.getClick().play(GameLogic.audioManager.settingsSound());
                GameLogic.audioManager.setMusicVolume(false);
            }
        });

        musicOff = new Button(musicOffStyle);
        if (!GameLogic.audioManager.getMusicVolume()) musicOff.setPosition(10, APP_HEIGHT - 140);
        else musicOff.setPosition(-100, APP_HEIGHT - 140);
        musicOff.setSize(60, 60);
        musicOff.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 1");
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 0");
                musicOff.setPosition(-100, APP_HEIGHT - 140);
                musicOn.setPosition(10, APP_HEIGHT - 140);
                GameLogic.audioManager.getClick().play(GameLogic.audioManager.settingsSound());
                GameLogic.audioManager.setMusicVolume(true);
            }
        });

        mainMenuGroup.addActor(play);
        mainMenuGroup.addActor(exit);
        mainMenuGroup.addActor(soundOn);
        mainMenuGroup.addActor(soundOff);
        mainMenuGroup.addActor(musicOn);
        mainMenuGroup.addActor(musicOff);
        stage.addActor(mainMenuGroup);
    }

    private void initRestart(Stage stage) {
        restartGroup = new Group();
        restartGroup.setPosition(0, APP_HEIGHT + 300);

        restartStyle = new Button.ButtonStyle();
        restartStyle.down = skin.getDrawable("restartDown");
        restartStyle.up = skin.getDrawable("restartUp");

        exitStyle = new Button.ButtonStyle();
        exitStyle.down = skin.getDrawable("exitDown");
        exitStyle.up = skin.getDrawable("exitUp");

        restart = new Button(restartStyle);
        restart.setPosition(350f, 270f);
        restart.setSize(70f, 70f);
        restart.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("RESTART: 1");
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("RESTART: 0");
                GameLogic.restartGame();
                GameLogic.score = 0;
                GameLogic.audioManager.getClick().play(GameLogic.audioManager.settingsSound());
            }
        });

        exit = new Button(exitStyle);
        exit.setPosition(430f, 270);
        exit.setSize(70f, 70f);
        exit.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 1");
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 0");
                GameLogic.audioManager.getClick().play(GameLogic.audioManager.settingsSound());
                Gdx.app.exit();
            }
        });

        restartGroup.addActor(restart);
        restartGroup.addActor(exit);
        stage.addActor(restartGroup);
    }

    private void initNext(Stage stage) {
        nextGroup = new Group();
        nextGroup.setPosition(0, APP_HEIGHT + 300);

        nextStyle = new Button.ButtonStyle();
        nextStyle.down = skin.getDrawable("nextDown");
        nextStyle.up = skin.getDrawable("nextUp");

        exitStyle = new Button.ButtonStyle();
        exitStyle.down = skin.getDrawable("exitDown");
        exitStyle.up = skin.getDrawable("exitUp");

        next = new Button(nextStyle);
        next.setPosition(350f, 270f);
        next.setSize(70f, 70f);
        next.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("NEXT: 1");
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("NEXT: 0");
                GameLogic.audioManager.getClick().play(GameLogic.audioManager.settingsSound());
                GameLogic.restartGame();
            }
        });

        exit = new Button(exitStyle);
        exit.setPosition(430f, 270);
        exit.setSize(70f, 70f);
        exit.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 1");
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("EXIT: 0");
                GameLogic.audioManager.getClick().play(GameLogic.audioManager.settingsSound());
                Gdx.app.exit();
            }
        });

        nextGroup.addActor(next);
        nextGroup.addActor(exit);
        stage.addActor(nextGroup);
    }
}
