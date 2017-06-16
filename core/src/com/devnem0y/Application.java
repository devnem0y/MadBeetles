package com.devnem0y;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devnem0y.managers.GameScreenManager;

public class Application extends Game {

	public GameScreenManager gsm;
	public static AssetManager assetManager;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		gsm = new GameScreenManager(this);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		assetManager.dispose();
		gsm.dispose();
	}
}
