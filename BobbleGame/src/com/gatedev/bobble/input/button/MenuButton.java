package com.gatedev.bobble.input.button;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.gatedev.bobble.utils.OverlapTester;

public class MenuButton extends Button {
	public String text;
	public int id, initX, finalX;
    public boolean visible = true;
    public final int step = 15;

	public MenuButton(int x, int y, int width, int height, String text, int id, TextureRegion img, int finalX) {
        super(x, y, new Rectangle(finalX, y, width, height), img);
		this.text = text;
		this.id = id;
        this.initX = x;
        this.finalX = finalX;
	}

    public void tick() {
        if(x<finalX) {
            if(finalX-x<step) x = finalX;
            else x+=step;
        }
        else if(x>finalX) {
            if(x-finalX<=step) x = finalX;
            else x-=step;
        }
    }

    public boolean isPressed(Vector3 tp) {
        if(OverlapTester.pointInRectangle(bounds, tp.x, tp.y)) {
            return true;
        }
        else return false;
    }
	
	public void render(SpriteBatch batch, BitmapFont font) {
        super.render(batch);
		font.draw(batch, text, x+15, y+25);
	}
}
