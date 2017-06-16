package com.devnem0y.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.devnem0y.Application;

import static com.devnem0y.utils.Constants.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = APP_TITLE + APP_VERSION;
		config.width = DESKTOP_WIDTH;
		config.height = DESKTOP_HEIGHT;
		config.backgroundFPS = APP_FPS;
		config.resizable = false;
		config.fullscreen = false;
		new LwjglApplication(new Application(), config);
	}
}
