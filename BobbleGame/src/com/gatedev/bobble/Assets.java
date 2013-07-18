package com.gatedev.bobble;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    public static Texture stageTexture;
    public static TextureRegion stage;

    public static Texture buttonsTexture;
    public static TextureRegion leftButton;
    public static TextureRegion rightButton;
    public static TextureRegion shootButton;
    public static TextureRegion pauseButton;

    public static Texture bubblesTexture;
    public static TextureRegion redBubble;
    public static TextureRegion blueBubble;
    public static TextureRegion greenBubble;
    public static TextureRegion yellowBubble;

    public static Texture arrowTexture;
    public static Sprite arrow;

	public static void loadTextures() {
        stageTexture = load("data/stage.png");
        stage = new TextureRegion(stageTexture, 0, 0, 480, 800);

        bubblesTexture = load("data/bubbles.png");
        redBubble = new TextureRegion(bubblesTexture, 128, 0, 64, 64);
        blueBubble = new TextureRegion(bubblesTexture, 64, 0, 64, 64);
        greenBubble = new TextureRegion(bubblesTexture, 192, 0, 64, 64);
        yellowBubble = new TextureRegion(bubblesTexture, 0, 0, 64, 64);

        arrowTexture = load("data/arrow.png");
        arrow = new Sprite(arrowTexture, 0, 0, 150, 64);

        buttonsTexture = load("data/buttons.png");
        leftButton = new TextureRegion(buttonsTexture, 0, 0, 50, 35);
        rightButton = new TextureRegion(buttonsTexture, 64, 0, 50, 35);
        shootButton = new TextureRegion(buttonsTexture, 128, 0, 60, 60);
        pauseButton = new TextureRegion(buttonsTexture, 224, 0, 37, 29);

        try {
            //load sounds
        } catch (final Exception ex) {
            try {
                Gdx.app.log("Sound Load Error", "Failed to load sounds, trying again!");
            } catch (final Exception ex1) {
                Gdx.app.log("Sound Load Error", "Failed to load sounds (second time), exiting application!");
            }
        }
	}
	
	public static Texture load(String path) {
		return new Texture(Gdx.files.internal(path));
	}
	
	public static Music loadSound(String path) {
		return Gdx.audio.newMusic(Gdx.files.getFileHandle(path, FileType.Internal));
	}
	
	public static Sound loadShortSound(String path) {
		return Gdx.audio.newSound(Gdx.files.getFileHandle(path, FileType.Internal));
	}
}
