package com.devnem0y.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.devnem0y.Application;
import com.devnem0y.managers.GameScreenManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.devnem0y.utils.Constants.APP_HEIGHT;
import static com.devnem0y.utils.Constants.APP_WIDTH;

public class SplashScreen extends AbstractScreen{

    private OrthographicCamera camera;

    public SplashScreen(final Application app) {
        super(app);
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, APP_WIDTH, APP_HEIGHT);
        stage = new Stage(new FitViewport(APP_WIDTH, APP_HEIGHT, camera));
    }

    @Override
    public void show() {
        System.out.println("LOGO");
        Gdx.input.setInputProcessor(stage);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                app.gsm.setScreen(GameScreenManager.STATE.GAME);
            }
        };

        Texture texture = app.assetManager.get("image/logo.png", Texture.class);
        Image splashImg = new Image(texture);
        splashImg.setPosition(0, 0);
        splashImg.addAction(sequence(alpha(0), fadeIn(3f), delay(0.8f), fadeOut(2f), run(transitionRunnable)));

        stage.addActor(splashImg);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        app.batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

}
