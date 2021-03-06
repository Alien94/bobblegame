package com.gatedev.bobble.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gatedev.bobble.Assets;
import com.gatedev.bobble.entity.Arrow;
import com.gatedev.bobble.entity.Bubble;
import com.gatedev.bobble.entity.Entity;
import com.gatedev.bobble.screen.GameScreen;

import java.util.ArrayList;

/**
 * User: Gianluca
 * Date: 15/07/13
 * Time: 16.14
 */
public class Level {
    public GameScreen game;
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public int[] xCols, yRows;
    public Bubble[][] bubbles;
    public Arrow arrow;

    public Level(GameScreen game) {
        this.game = game;
        arrow = new Arrow(165, 80, game.keyboard);
        entities.add(arrow);
        xCols = new int[] {0, 32, 64, 96, 128, 160, 192, 224, 256, 288, 320, 352, 384};
        yRows = new int[] {64, 118, 172, 226, 280, 334, 388, 442, 498, 552, 606, 660};
        bubbles = new Bubble[12][13];
        loadMap();
    }

    public void tick() {
        tickEntities();
    }

    public void tickEntities() {
        for(int i=0; i<entities.size(); i++) {
            entities.get(i).tick(this);
        }
    }

    public void renderEntities(SpriteBatch batch) {
        for(Entity e : entities) {
            e.render(batch);
        }
    }

    public ArrayList<Bubble> getNearBubbles(Entity e) {
        ArrayList<Bubble> nearBubbles = new ArrayList<Bubble>();
        for(Entity k : entities) {
            if(k instanceof Bubble && k!=e) {
                nearBubbles.add((Bubble)k);
            }
        }
        return nearBubbles;
    }

    public void addBubble(float x, float y, int row, int col, TextureRegion img) {
        Bubble bubble = new Bubble(x, y, img);
        bubble.positionSetted = true;
        bubbles[row][col] = bubble;
        entities.add(bubble);
    }

    private void loadMap() {
        Pixmap minimage = new Pixmap(Gdx.files.internal("data/levels/lev-1.png"));

        for(int j=10; j>=0; j--) {
            for(int i=0; i<13; i++) {
                String color = Integer.toHexString(minimage.getPixel(i, j));
                float x=0, y=0;
                x = xCols[i];
                y = 750-yRows[j];
                if(color.equals("ffff00ff")) {
                    addBubble(x, y, j, i, Assets.yellowBubble);
                    System.out.println("Added yellow bubble Row:"+j+"("+y+")  col:"+i+" ("+x+")   "+color);
                }
                else if(color.equals("ff0000ff")) {
                    addBubble(x, y, j, i, Assets.redBubble);
                    System.out.println("Added red bubble Row:"+j+"("+y+")  col:"+i+" ("+x+")   "+color);
                }
                else if(color.equals("ff00ff")) {
                    addBubble(x, y, j, i, Assets.greenBubble);
                    System.out.println("Added green bubble Row:"+j+"("+y+")  col:"+i+" ("+x+")   "+color);
                }
                else if(color.equals("ffff")) {
                    addBubble(x, y, j, i, Assets.blueBubble);
                    System.out.println("Added blue bubble Row:"+j+"("+y+")  col:"+i+" ("+x+")   "+color);
                }
                else System.out.println("Row:"+j+"  col:"+i+"   "+color);
            }
        }
    }
}
