package com.gatedev.bobble.input.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.gatedev.bobble.Renderer;
import com.gatedev.bobble.utils.OverlapTester;

public class GameButton extends Button {

	private boolean touched = false;
	private String text = null;
	
	public GameButton(float x, float y, int width, int height, TextureRegion img) {
		super(x, y, new Rectangle(x, y, width, height), img);
	}
	
	public GameButton(float x, float y, TextureRegion img, int width, int height, String text) {
        super(x, y, new Rectangle(x, y, width, height), img);
		this.text = text;
	}
	
	public void tick() {
		for(int i=0; i<2; i++) {
			if(Gdx.input.isTouched(i)) {
				Vector3 coord = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				Renderer.guiCamera.unproject(coord);
				if(OverlapTester.pointInRectangle(bounds, coord.x, coord.y)) {
					touched = true;
					break;
				}
				else touched = false;
			}
			else touched = false;
		}
	}
	
	public boolean getInfo() {
		return touched;
	}
	
	public int getFire() {
		if(touched) return 1;
		else return 0;
	}
	
	public void render(SpriteBatch batch, BitmapFont font) {
		super.render(batch);
		if(text!=null) {
			TextBounds w = font.getBounds(text);
			font.draw(batch, text, x+70-w.width/2, y+35);
		}
	}
}
