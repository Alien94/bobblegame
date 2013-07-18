package com.gatedev.bobble.ui.elements;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Notification {
    protected float x, y, size, alpha = 1.0f;
    public boolean changingColor;
    public int time;
    protected String text;

    public Notification(float x, float y, float size, int time, String text, boolean changingColor) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.time = time;
        this.text = text;
        this.changingColor = changingColor;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        font.setScale(size);
        if(changingColor && time%4==0) font.setColor(0, 0, 0, alpha);
        else font.setColor(1, 1, 1, alpha);
        font.draw(batch, text, x, y);
        time--;
        if(time<=12 && time%2==0) alpha-=0.1f;
    }
}
