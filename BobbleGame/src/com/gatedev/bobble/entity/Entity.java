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
    private ArrayList<Bubble> checked = new ArrayList<Bubble>();

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(Level level) {

        Vector2 tempVelocity = new Vector2(velocity.x, velocity.y);
        float tempX = x + tempVelocity.x;
        float tempY = y + tempVelocity.y;
        boolean blocked = false;


        if(!positionSetted && tempY+height>750) {
            blocked = true;
            //velocity.y = 750-(y+height);
            y += 750-(y+height);
            velocity.x = 0;
            velocity.y = 0;

            int row = (int) ((750-(tempY+32)) / 54);
            //int col = (int)((x+32) / 64) * 2;
            int col = (int)(((tempX+32) / 64) * 2);
            double dec = (((tempX+32) / 64)-((int)((tempX+32) / 64)));
            double fris =  ((tempX+32) / 64);
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
            level.bubbles[row][col] =  (Bubble)this;
            System.out.println("AFTER be at row:"+row+"  col:"+col+"   x:"+tempX+"   y:"+tempY);
            positionSetted = true;
            level.arrow.nextShoot = 0;

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

        if(this instanceof Bubble) {
            ArrayList<Bubble> nearBubbles = level.getNearBubbles(this);
            for(Bubble b : nearBubbles) {
                if (Math.sqrt(((tempX+32)-(b.x+32))*((tempX+32)-(b.x+32))+((tempY+32)-(b.y+32))*((tempY+32)-(b.y+32)))<(30+30)) {
                    if(!positionSetted) {
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
                        level.bubbles[row][col] =  (Bubble)this;
                        System.out.println("AFTER be at row:"+row+"  col:"+col+"   x:"+tempX+"   y:"+tempY);
                        positionSetted = true;
                        level.arrow.nextShoot = 0;

                        checked.clear();

                        /*
                        ArrayList<Bubble> around = new ArrayList<Bubble>();
                        addToList(level, around, row+1, col-1);
                        addToList(level, around, row+1, col+1);
                        addToList(level, around, row, col-2);
                        addToList(level, around, row, col+2);
                        addToList(level, around, row-1, col-1);
                        addToList(level, around, row-1, col+1);
                        System.out.println("Bubbles around: "+around.size());
                        */

                        int count = explode(level, row, col);
                        for(Bubble ba : checked) {
                            System.out.println("Bubble  row:"+ba.row+"   col:"+ba.col);
                        }
                        if(count>2) {
                            for(Bubble ba : checked) {
                                level.bubbles[ba.row][ba.col] = null;
                                level.entities.remove(ba);
                            }
                        }
                        System.out.println("Explode: "+count);

                        if(row>=10 && (col>=4 && col<=8)) {
                            System.out.println("DIED!");
                        }
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

    private boolean isValid(Level level, int r, int c) {
        if(r<=11 && r>=0 && c>=0 && c<13 && level.bubbles[r][c]!=null) {
            return true;
        }
        else return false;
    }

    private int explode(Level level, int r, int c) {
        System.out.println("Exploding row:"+(r)+"  col:"+(c)+"  color:"+level.bubbles[r][c].color);

        int count = 0;
        boolean found = false;

        if(isValid(level, r+1, c-1)) {
            for(Bubble b:checked) {
                if(level.bubbles[r+1][c-1]==b) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                if(level.bubbles[r+1][c-1].color == ((Bubble)this).color) {
                    count = explode(level, r+1, c-1);
                    count++;
                    System.out.println("1 Count: "+count);
                    return count;
                }
            }
        }

        if(isValid(level, r+1, c+1)) {
            found = false;
            for(Bubble b:checked) {
                if(level.bubbles[r+1][c+1]==b) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                if(level.bubbles[r+1][c+1].color == ((Bubble)this).color) {
                    checked.add(level.bubbles[r][c]);
                    count = explode(level, r+1, c+1);
                    count++;
                    System.out.println("2 Count: "+count);
                    return count;
                }
            }
        }

        if(isValid(level, r, c-2)) {
            found = false;
            for(Bubble b:checked) {
                if(level.bubbles[r][c-2]==b) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                if(level.bubbles[r][c-2].color == ((Bubble)this).color) {
                    checked.add(level.bubbles[r][c]);
                    count = explode(level, r, c-2);
                    count++;
                    System.out.println("3 Count: "+count+"   analyzing row:"+r+"  col:"+c);
                    return count;
                }
            }
        }

        if(isValid(level, r, c+2)) {
            System.out.println("Valid max row:"+(r)+"  col:"+(c+2)+"  color:"+level.bubbles[r][c+2].color);
            found = false;
            for(Bubble b:checked) {
                if(level.bubbles[r][c+2]==b) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                if(level.bubbles[r][c+2].color == ((Bubble)this).color) {
                    checked.add(level.bubbles[r][c]);
                    count = explode(level, r, c+2);
                    count++;
                    System.out.println("4 Count: "+count);
                    return count;
                }
            }
        }

        if(isValid(level, r-1, c-1)) {
            System.out.println("Valid min row:"+(r-1)+"  col:"+(c-1)+"  color:"+level.bubbles[r-1][c-1].color);
            found = false;
            for(Bubble b:checked) {
                if(level.bubbles[r-1][c-1]==b) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                if(level.bubbles[r-1][c-1].color == ((Bubble)this).color) {
                    checked.add(level.bubbles[r][c]);
                    count = explode(level, r-1, c-1);
                    count++;
                    System.out.println("5 Count: "+count);
                    return count;
                }
            }
        }

        if(isValid(level, r-1, c+1)) {
            System.out.println("Valid max row:"+(r-1)+"  col:"+(c+1)+"  color:"+level.bubbles[r-1][c+1].color);
            found = false;
            for(Bubble b:checked) {
                if(level.bubbles[r-1][c+1]==b) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                if(level.bubbles[r-1][c+1].color == ((Bubble)this).color) {
                    checked.add(level.bubbles[r][c]);
                    count = explode(level, r-1, c+1);
                    count++;
                    System.out.println("6 Count: "+count);
                    return count;
                }
            }
        }

        checked.add(level.bubbles[r][c]);

        return 1;
    }

    private void addToList(Level level, ArrayList<Bubble> list, int r, int c) {
        if(r<=11 && r>=0 && c>=0 && c<13) {
            if(level.bubbles[r][c]!=null) list.add(level.bubbles[r][c]);
        }
    }

    public abstract void render(SpriteBatch batch);

    public Rectangle getCollisionRect(float x, float y) {
        return new Rectangle(x, y, width, height);
    }
}

