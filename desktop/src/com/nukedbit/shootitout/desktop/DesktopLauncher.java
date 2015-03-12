package com.nukedbit.shootitout.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nukedbit.shootitout.ShootItOut;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.vSyncEnabled = true;
        config.width = 800;
        config.height = 600;
		new LwjglApplication(new ShootItOut(), config);
	}
}