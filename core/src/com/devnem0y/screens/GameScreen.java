package com.devnem0y.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.devnem0y.Application;
import com.devnem0y.handlers.GameLogic;
import com.devnem0y.handlers.input.ButtonsMenu;
import com.devnem0y.handlers.input.Controller;
import com.devnem0y.objects.StartTimer;

import static com.devnem0y.utils.Constants.*;

public class GameScreen extends AbstractScreen{

    public enum GameState {
        MENU,
        START, // timer
        PLAY,
        PAUSE,
    }

    //======================
    public static boolean maskShow = false;
    //======================

    private OrthographicCamera camera;
    public static GameState gameState;
    private GameLogic gameLogic;
    private Texture background, bit;
    private static float timerStart;
    public static StartTimer timer;

    private ButtonsMenu menu;
    public static Controller controller;
    public static int idButton;

    public GameScreen(final Application app) {
        super(app);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, APP_WIDTH, APP_HEIGHT);

        stage.setViewport(new StretchViewport(APP_WIDTH, APP_HEIGHT, camera));
    }

    @Override
    public void show() {
        System.out.println("GAME");
        stage.clear();
        background = new Texture("image/bg.jpg");
        bit = new Texture("image/bit_menu.png");
        gameLogic = new GameLogic();
        gameLogic.create();
        menu = new ButtonsMenu();
        menu.createMenu(stage);
        controller = new Controller();
        controller.createJoy(stage);
        timerStart = 6f;
        timer = new StartTimer();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float delta) {
        camera.update();
        stage.act(delta);
        switch (gameState) {
            case MENU:
                menu.mainMenuGroup.addAction(Actions.moveTo(0, 0, 0.8f));
                gameLogic.updateMenu();
                break;
            case START:
                gameLogic.updateStart();
                menu.mainMenuGroup.addAction(Actions.moveTo(0, APP_HEIGHT + 300, 0.9f));
                menu.restartGroup.addAction(Actions.moveTo(0, APP_HEIGHT + 300, 0.9f));
                menu.nextGroup.addAction(Actions.moveTo(0, APP_HEIGHT + 300, 0.9f));
                if (timerStart <= 0.5f) gameState = GameState.PLAY;
                timerStart -= delta;
                break;
            case PLAY:
                controller.joyGroup.addAction(Actions.moveTo(0, 0, 0.2f));
                gameLogic.updatePlay(delta);
                break;
            case PAUSE:
                gameLogic.updatePause();
                controller.joyGroup.addAction(Actions.moveTo(0, -270, 0.2f));
                if (GameLogic.player.isAlive()) {
                    menu.nextGroup.addAction(Actions.moveTo(0, 0, 0.9f));
                } else {
                    menu.restartGroup.addAction(Actions.moveTo(0, 0, 0.9f));
                }
                timerStart = 5f;
                break;
            default:
                break;
        }

        gameLogic.updateLevel(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            maskShow = !maskShow;
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        app.batch.setProjectionMatrix(camera.combined);
        app.batch.begin();
        app.batch.draw(background, 0, 0, APP_WIDTH, APP_HEIGHT);
        switch (gameState) {
            case MENU:
                app.batch.draw(bit, 10, 0);
                break;
            case START:
                if ((int)timerStart < 5 && (int)timerStart > 0) timer.render(app.batch, delta);
                gameLogic.drawHUD(app.batch);
                break;
            case PLAY:
                gameLogic.renderPlay(app.batch, delta);
                break;
            case PAUSE:
                gameLogic.renderPause(app.batch, delta);
                break;
            default:
                break;
        }
        app.batch.end();
        stage.draw();
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
        super.dispose();
        timer.dispose();
        gameLogic.dispose();
    }
}
