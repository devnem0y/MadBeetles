package com.devnem0y.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.devnem0y.Application;
import com.devnem0y.managers.GameScreenManager;

import static com.devnem0y.utils.Constants.*;

public class LoadingScreen extends AbstractScreen{

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private float progress;
    private float midY;

    public LoadingScreen(final Application app) {
        super(app);
        this.camera = new OrthographicCamera();
        this.shapeRenderer = new ShapeRenderer();
        camera.setToOrtho(false, APP_WIDTH, APP_HEIGHT);
        stage = new Stage(new StretchViewport(APP_WIDTH, APP_HEIGHT, camera));
        midY = APP_SCREEN_HEIGHT / 2;
    }

    @Override
    public void show() {
        System.out.println("LOADING");
        this.progress = 0f;
        queueAssets();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        progress = MathUtils.lerp(progress, app.assetManager.getProgress(), 0.1f);
        if (app.assetManager.update() && progress >= app.assetManager.getProgress() - 0.001f) {
            app.gsm.setScreen(GameScreenManager.STATE.SPLASH);
        }
    }

    @Override
    public void render(float delta) {
        app.batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(32, midY - 8, APP_SCREEN_WIDTH - 64, 16);

        shapeRenderer.setColor(Color.FOREST);
        shapeRenderer.rect(32, midY - 8, progress * (APP_SCREEN_WIDTH - 64), 16);
        shapeRenderer.end();
        stage.draw();
    }

    private void queueAssets() {
        // Logo
        app.assetManager.load("image/logo.png", Texture.class);
        // TextureAtlas

        app.assetManager.finishLoading();
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

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
