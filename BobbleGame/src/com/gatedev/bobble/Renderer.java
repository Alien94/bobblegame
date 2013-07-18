package com.gatedev.bobble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.gatedev.bobble.screen.GameScreen;

public class Renderer {
	public static OrthographicCamera camera;
	public static OrthographicCamera guiCamera;
	public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
	private GameScreen game;
	private BitmapFont font;
	public BitmapFont font_stroke;
	
	public Renderer(GameScreen game) {
        camera = new OrthographicCamera(480, 800);
		guiCamera = new OrthographicCamera(480, 800);
		batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
		this.game = game;
		font_stroke = new BitmapFont(Gdx.files.internal("data/fonts/superretro.fnt"), Gdx.files.internal("data/fonts/superretro.png"), false);
		font_stroke.setScale(1.7f);
		font_stroke.setColor(Color.WHITE);
		font = new BitmapFont(Gdx.files.internal("data/fonts/harabara.fnt"), Gdx.files.internal("data/fonts/harabara.png"), false);
		font.setScale(0.6f);
		Vector3 position = camera.position;
		position.x += 240;
		position.y += 400;
	}

	public void render() {
        shapeRenderer.setProjectionMatrix(camera.combined);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
        batch.draw(Assets.stage, 0, 0);
        game.level.renderEntities(batch);
        //batch.draw(Assets.bubble, 0, 0);
		batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 1, 1);
        shapeRenderer.line(game.level.arrow.x+75, game.level.arrow.y+32, game.level.arrow.x+75+((float)Math.cos(Math.toRadians(game.level.arrow.degrees))*600), game.level.arrow.y+32+((float)Math.sin(Math.toRadians(game.level.arrow.degrees))*600));
        shapeRenderer.end();
		
		batch.begin();
		batch.setProjectionMatrix(guiCamera.combined);
        game.controls.render(batch);
        font.setColor(Color.WHITE);
        font.setScale(0.5f);
        font.draw(batch, "FPS: "+game.curFps, -230, 380);
        batch.end();
	}
}
