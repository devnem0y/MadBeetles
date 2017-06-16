package com.devnem0y.managers;

import com.devnem0y.Application;
import com.devnem0y.screens.AbstractScreen;
import com.devnem0y.screens.GameScreen;
import com.devnem0y.screens.LoadingScreen;
import com.devnem0y.screens.SplashScreen;

import java.util.HashMap;

public class GameScreenManager {

    private final Application app;
    private HashMap<STATE, AbstractScreen> gameScreens;

    public enum STATE {
        LOADING,
        SPLASH,
        GAME,
    }

    public GameScreenManager(final Application app) {
        this.app = app;
        initGameScreens();
        setScreen(STATE.LOADING);
    }

    private void initGameScreens() {
        this.gameScreens = new HashMap<STATE, AbstractScreen>();
        this.gameScreens.put(STATE.LOADING, new LoadingScreen(app));
        this.gameScreens.put(STATE.SPLASH, new SplashScreen(app));
        this.gameScreens.put(STATE.GAME, new GameScreen(app));
    }

    public void setScreen(STATE nextScreen) {
        app.setScreen(gameScreens.get(nextScreen));
    }

    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if (screen != null) screen.dispose();
        }
    }

}
