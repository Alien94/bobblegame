package com.gatedev.bobble.input.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Button {
    public float x, y;
    public Rectangle bounds;
    private TextureRegion img;

    public Button(float x, float y, Rectangle bounds, TextureRegion img) {
        this.x = x;
        this.y = y;
        this.bounds = bounds;
        this.img = img;
    }

    public void render(SpriteBatch batch) {
        if(img!=null) batch.draw(img, x, y);
    }
}
