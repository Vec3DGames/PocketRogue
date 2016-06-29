package xyz.vec3d.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by darakelian on 6/28/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Core game class. Contains references to global objects such as the AssetManager.
 */
public class PocketRogue extends Game {

	private AssetManager assetManager = new AssetManager();

	public void create () {
		setScreen(new LoadingScreen(this));
	}

	public void render () {
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
		super.render();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
