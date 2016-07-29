package xyz.vec3d.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by darakelian on 6/28/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Core game class. Contains references to global objects such as the AssetManager.
 */
public class PocketRogue extends Game {

	private AssetManager assetManager = new AssetManager();

	private static PocketRogue _instance = new PocketRogue();

	public PocketRogue() {
		_instance = this;
	}

	public void create () {
		setScreen(new LoadingScreen(this));
	}

	public void render () {
		super.render();
	}

	public AssetManager getAssets() {
		return assetManager;
	}

	public static PocketRogue getInstance() {
		if (_instance == null) {
			return new PocketRogue();
		}
		return _instance;
	}

	public static AssetManager getAssetManager() {
		return getInstance().getAssets();
	}
}
