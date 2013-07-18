package com.gatedev.bobble.ui.elements;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SlideNotification {
	public String text;
	public int time = 180;
	private float y = -200;
	
	public SlideNotification(String text) {
		this.text = text;
	}
	
	public void render(SpriteBatch batch, BitmapFont font) {
        font.setColor(1, 1, 1, 1);
		//batch.draw(Assets.notificationBar, -200, y);
		float x = -font.getBounds(text).width/2;
		font.draw(batch, text, x, y+23);
		if(time>140) y++;
		else if(time<+40) y--;
		time--;
	}
}
