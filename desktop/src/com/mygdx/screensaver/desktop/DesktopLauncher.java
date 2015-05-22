package com.mygdx.screensaver.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.screensaver.Screensaver;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = 1440;
                config.height = 900;
//                config.fullscreen = true;
		new LwjglApplication(new Screensaver(), config);
	}
}
