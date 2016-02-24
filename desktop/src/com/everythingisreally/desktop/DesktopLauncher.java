package com.everythingisreally.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.everythingisreally.StarWhale;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Starwhale";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new StarWhale(), config);
	}
}
