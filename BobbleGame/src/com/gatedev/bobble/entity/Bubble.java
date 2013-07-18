package com.gatedev.bobble.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gatedev.bobble.Assets;

/**
 * User: Gianluca
 * Date: 15/07/13
 * Time: 16.12
 */
public class Bubble extends Entity {

    public int row, col;
    public TextureRegion img;

    public Bubble(float x, float y, TextureRegion img) {
        super(x, y);
        this.width = 64;
        this.height = 64;
        this.img = img;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(img, x+16, y);
    }
}
