package xyz.vec3d.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import xyz.vec3d.game.*;

/**
 * Created by darakelian on 6/28/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Main entry class for desktop application. Sets up the app with the settings
 * specified in the Settings.java file.
 */
public class DesktopLauncher {

	/**
	 *
	 * @param arg
     */
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = xyz.vec3d.game.Settings.WIDTH;
		config.height = xyz.vec3d.game.Settings.HEIGHT;
		new LwjglApplication(new PocketRogue(), config);
	}

}
