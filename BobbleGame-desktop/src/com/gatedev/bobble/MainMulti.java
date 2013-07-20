package com.gatedev.bobble;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MainMulti {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "BobbleGame";
		cfg.useGL20 = false;
		cfg.width = 960;
		cfg.height = 800;
		
		new LwjglApplication(new BobbleGame(), cfg);
	}
}
