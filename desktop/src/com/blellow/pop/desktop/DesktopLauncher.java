package com.blellow.pop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blellow.pop.BlellowPop;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Blellow Pop";
        config.width = 900;
        config.height = 600;
        config.vSyncEnabled = true;

		new LwjglApplication(new BlellowPop(), config);
	}
}
