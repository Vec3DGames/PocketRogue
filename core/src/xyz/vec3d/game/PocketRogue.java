package xyz.vec3d.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

import xyz.vec3d.game.gfx.SpriteSheet;
import xyz.vec3d.game.utils.Logger;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by darakelian on 6/28/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Core game class. Contains references to global objects such as the AssetManager.
 */
public class PocketRogue extends Game {

	private AssetManager assetManager = new AssetManager();

	private SpriteSheet[] spriteSheets;

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

	public void setSpriteSheets(SpriteSheet[] spriteSheets) {
		this.spriteSheets = spriteSheets;
	}

	public SpriteSheet getSpriteSheet(int itemId) {
		return spriteSheets[itemId / 256];
	}

	public static <T> T getAsset(String name) {
		return getAsset(name, true);
	}

	public static <T> T getAsset(String name, boolean managed) {
		if (managed) {
			return getAssetManager().get("./managed_assets/" + name);
		}
		return getAssetManager().get(name);
	}
}
