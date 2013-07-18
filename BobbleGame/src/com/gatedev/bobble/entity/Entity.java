package com.gatedev.bobble.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gatedev.bobble.level.Level;
import com.gatedev.bobble.utils.OverlapTester;

import java.util.ArrayList;

/**
 * User: Gianluca
 * Date: 15/07/13
 * Time: 16.11
 */
public abstract class Entity {

    public float x;
    public float y;
    public int width;
    public int height;
    public Vector2 velocity = new Vector2(0, 0);
    public boolean toRemove = false;
    public boolean positionSetted = false;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(Level level) {

        Vector2 tempVelocity = new Vector2(velocity.x, velocity.y);
        float tempX = x + tempVelocity.x;
        float tempY = y + tempVelocity.y;
        boolean blocked = false;


        if(tempY+height>750) {
            blocked = true;
            //velocity.y = 750-(y+height);
            y += 750-(y+height);
            velocity.x = 0;
        }

        if(tempX+width>448) {
            blocked = true;
            //velocity.y = 750-(y+height);
            x += 448-(x+width);
            velocity.x = -velocity.x;
        }

        if(tempX<0) {
            blocked = true;
            //velocity.y = 750-(y+height);
            x -= x;
            velocity.x = -velocity.x;
        }


                                      /*
        if(this instanceof Bubble) {
            ArrayList<Bubble> nearBubbles = level.getNearBubbles(this);
            for(Bubble b : nearBubbles) {
                if (!positionSetted && Math.sqrt(((tempX+32)-(b.x+32))*((tempX+32)-(b.x+32))+((tempY+32)-(b.y+32))*((tempY+32)-(b.y+32)))<(30+30)) {
                    System.out.println("collide with: "+b+"  at row:"+b.row+"   col:"+b.col);
                    velocity.x = 0;
                    velocity.y = 0;
                    int row = (int) ((750-(y+32)) / 54);
                    //int col = (int)((x+32) / 64) * 2;
                    int col = (int)(((x+32) / 64) * 2);
                    double dec = (((x+32) / 64)-((int)((x+32) / 64)));
                    double fris =  ((x+32) / 64);
                    System.out.println("BEFORE be at row:"+row+"  col:"+col+"   x:"+x+"   y:"+y+"    dec:"+dec+"   fRis:"+fris);
                    y = 750-level.yRows[row];
                    if(row%2==0) {
                        if(col%2==1) {
                            if(dec<0.5) col++;
                            else col--;
                        }
                        x = level.xCols[col];
                    }
                    else {
                        if(col%2==0) {
                            col--;
                        }
                        if(col==12) col = 11;
                        x=level.xCols[col];
                    }
                    ((Bubble)this).row = row;
                    ((Bubble)this).col = col;
                    System.out.println("AFTER be at row:"+row+"  col:"+col+"   x:"+x+"   y:"+y);
                    positionSetted = true;
                    level.arrow.nextShoot = 0;

                    if(row>=10 && (col>=4 && col<=8)) {
                        System.out.println("DIED!");
                    }
                }
            }


        }
        */
        if(this instanceof Bubble) {
            ArrayList<Bubble> nearBubbles = level.getNearBubbles(this);
            for(Bubble b : nearBubbles) {
                if (!positionSetted && Math.sqrt(((tempX+32)-(b.x+32))*((tempX+32)-(b.x+32))+((tempY+32)-(b.y+32))*((tempY+32)-(b.y+32)))<(30+30)) {
                    System.out.println("collide with: "+b+"  at row:"+b.row+"   col:"+b.col);
                    velocity.x = 0;
                    velocity.y = 0;
                    int row = (int) ((750-(tempY+32)) / 54);
                    //int col = (int)((x+32) / 64) * 2;
                    int col = (int)(((tempX+32) / 64) * 2);
                    double dec = (((tempX+32) / 64)-((int)((tempX+32) / 64)));
                    double fris =  ((tempX+32) / 64);
                    System.out.println("BEFORE be at row:"+row+"  col:"+col+"   x:"+tempX+"   y:"+tempY+"    dec:"+dec+"   fRis:"+fris);
                    y = 750-level.yRows[row];
                    if(row%2==0) {
                        if(col%2==1) {
                            if(dec<0.5) col++;
                            else col--;
                        }
                        x = level.xCols[col];
                    }
                    else {
                        if(col%2==0) {
                            col--;
                        }
                        if(col==12) col = 11;
                        else if(col==13) col = 11;
                        else if(col==-1) col = 1;
                        //errore index -1
                        x=level.xCols[col];
                    }
                    ((Bubble)this).row = row;
                    ((Bubble)this).col = col;
                    System.out.println("AFTER be at row:"+row+"  col:"+col+"   x:"+tempX+"   y:"+tempY);
                    positionSetted = true;
                    level.arrow.nextShoot = 0;

                    if(row>=10 && (col>=4 && col<=8)) {
                        System.out.println("DIED!");
                    }
                }
            }


        }


        if(!blocked) {
            x += velocity.x;
            y += velocity.y;
            //System.out.println("New VeclX:"+velocity.x+"   VelY:"+velocity.y+"   Y:"+y);
        }
        else {
        }
    }

    public abstract void render(SpriteBatch batch);

    public Rectangle getCollisionRect(float x, float y) {
        return new Rectangle(x, y, width, height);
    }
}

