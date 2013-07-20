package com.gatedev.bobble.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gatedev.bobble.level.Level;
import com.gatedev.bobble.screen.GameScreen;
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
    public boolean positionSetted = false;
<<<<<<< HEAD
=======
    public boolean falling = false;
    public boolean toRemove = false;
>>>>>>> origin/gian

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
<<<<<<< HEAD
=======
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
            //System.out.println("AFTER be at row:"+row+"  col:"+col+"   x:"+tempX+"   y:"+tempY);
            positionSetted = true;
            level.arrow.nextShoot = 0;

            level.checked.clear();
            level.bubbleCount = 0;

            ArrayList<Bubble> around = giveAround(level, row, col);
            //System.out.println("Around to row:"+row+"  col:"+col+": "+around.size());
            addToCheckedList(level, (Bubble) this);
            level.bubbleCount++;
            //System.out.println("ADDED TO LIST row:"+row+"  col:"+col);
            for(Bubble be : around) {
                //System.out.println("Main explode");
                be.exp(level, false);
            }
            //System.out.println("Count: "+level.bubbleCount+"   Checked:"+level.checked.size());
            if(level.checked.size()>2) {
                for(Bubble ba : level.checked) {
                    //System.out.println("First Removed  row:"+ba.row+"   col:"+ba.col);
                    level.bubbles[ba.row][ba.col] = null;
                    //level.entities.remove(ba);
                    //ba.toRemove = true;
                    ba.falling = true;
                    //System.out.println("Current entities:"+level.entities.size());
                }
                level.toUpdateBubbles = 1;
            }

>>>>>>> origin/gian
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

<<<<<<< HEAD

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
=======
        if(falling) {
            velocity.y -= 0.8f;
            if(y<0) toRemove = true;
        }

        if(!falling && this instanceof Bubble) {
            ArrayList<Bubble> nearBubbles = level.getNearBubbles(this);
            for(Bubble b : nearBubbles) {
                if (Math.sqrt(((tempX+32)-(b.x+32))*((tempX+32)-(b.x+32))+((tempY+32)-(b.y+32))*((tempY+32)-(b.y+32)))<(30+30)) {
                    if(!positionSetted) {
                        //System.out.println("collide with: "+b+"  at row:"+b.row+"   col:"+b.col);
                        velocity.x = 0;
                        velocity.y = 0;
                        int row = (int) ((750-(tempY+32)) / 54);
                        //int col = (int)((x+32) / 64) * 2;
                        int col = (int)(((tempX+32) / 64) * 2);
                        double dec = (((tempX+32) / 64)-((int)((tempX+32) / 64)));
                        double fris =  ((tempX+32) / 64);
                        //System.out.println("BEFORE be at row:"+row+"  col:"+col+"   x:"+tempX+"   y:"+tempY+"    dec:"+dec+"   fRis:"+fris);
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
                            x=level.xCols[col];
                        }
                        ((Bubble)this).row = row;
                        ((Bubble)this).col = col;
                        level.bubbles[row][col] =  (Bubble)this;
                        //System.out.println("AFTER be at row:"+row+"  col:"+col+"   x:"+tempX+"   y:"+tempY);
                        positionSetted = true;
                        level.arrow.nextShoot = 0;

                        level.checked.clear();
                        level.bubbleCount = 0;

                        ArrayList<Bubble> around = giveAround(level, row, col);
                        //System.out.println("Around to row:"+row+"  col:"+col+": "+around.size());
                        addToCheckedList(level, (Bubble) this);
                        level.bubbleCount++;
                        //System.out.println("ADDED TO LIST row:"+row+"  col:"+col);
                        for(Bubble be : around) {
                            be.exp(level, false);
                        }
                        //System.out.println("Count: "+level.bubbleCount+"   Checked:"+level.checked.size());
                        if(level.checked.size()>2) {
                            for(Bubble ba : level.checked) {
                                //System.out.println("Second Removed  row:"+ba.row+"   col:"+ba.col);
                                level.bubbles[ba.row][ba.col] = null;
                                //level.entities.remove(ba);
                                //ba.toRemove = true;
                                ba.falling = true;
                                //System.out.println("Current entities:"+level.entities.size());
                            }
                            level.toUpdateBubbles = 1;
                        }

                        if(row>=10 && (col>=4 && col<=8)) {
                            System.out.println("DIED!");
>>>>>>> origin/gian
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

<<<<<<< HEAD
=======
    public void exp(Level level, boolean allColors) {
        level.bubbleCount++;
        //System.out.println("Exploding row:"+((Bubble)this).row+"   col:"+((Bubble)this).col);
        addToCheckedList(level, (Bubble) this);
        //System.out.println("ADDED 2 row:"+((Bubble)this).row+"  col:"+((Bubble)this).col);

        ArrayList<Bubble> around = new ArrayList<Bubble>();
        if(!allColors) around = giveAround(level, ((Bubble)this).row, ((Bubble)this).col);
        else around = giveAroundAllColors(level, ((Bubble)this).row, ((Bubble)this).col);

        /*
        for(Bubble be : around) {
            System.out.println("Around item  row:"+be.row+"  col:"+be.col);
        }
        */
        for(Bubble be : around) {
            be.exp(level, allColors);
        }
    }

    private ArrayList<Bubble> giveAround(Level level, int row, int col) {
        ArrayList<Bubble> around = new ArrayList<Bubble>();
        if(isValid(level, row+1, col-1) && level.bubbles[row+1][col-1].color==((Bubble)this).color) around.add(level.bubbles[row+1][col-1]);
        if(isValid(level, row+1, col+1) && level.bubbles[row+1][col+1].color==((Bubble)this).color) around.add(level.bubbles[row+1][col+1]);
        if(isValid(level, row, col-2) && level.bubbles[row][col-2].color==((Bubble)this).color) around.add(level.bubbles[row][col-2]);
        if(isValid(level, row, col+2) && level.bubbles[row][col+2].color==((Bubble)this).color) around.add(level.bubbles[row][col+2]);
        if(isValid(level, row-1, col-1) && level.bubbles[row-1][col-1].color==((Bubble)this).color ) around.add(level.bubbles[row-1][col-1]);
        if(isValid(level, row-1, col+1) && level.bubbles[row-1][col+1].color==((Bubble)this).color) around.add(level.bubbles[row-1][col+1]);
        //System.out.println("Give around for row:"+row+"  col:"+col+"   size:"+around.size());
        return around;
    }

    private ArrayList<Bubble> giveAroundAllColors(Level level, int row, int col) {
        ArrayList<Bubble> around = new ArrayList<Bubble>();
        if(isValid(level, row+1, col-1)) around.add(level.bubbles[row+1][col-1]);
        if(isValid(level, row+1, col+1)) around.add(level.bubbles[row+1][col+1]);
        if(isValid(level, row, col-2)) around.add(level.bubbles[row][col-2]);
        if(isValid(level, row, col+2)) around.add(level.bubbles[row][col+2]);
        if(isValid(level, row-1, col-1)) around.add(level.bubbles[row-1][col-1]);
        if(isValid(level, row-1, col+1)) around.add(level.bubbles[row-1][col+1]);
        //System.out.println("Give around for row:"+row+"  col:"+col+"   size:"+around.size());
        return around;
    }

    private void addToCheckedList(Level level, Bubble b) {
        if(!level.checked.contains(b)) {
            level.checked.add(b);
            //System.out.println("Added to checked list  row:"+b.row+"   col:"+b.col);
        }
    }

    private boolean isValid(Level level, int r, int c) {
        //System.out.println("is valid?? row:"+r+"  col:"+c);
        //for(Bubble be : checked) {
            //System.out.println("CHECKED item  row:"+be.row+"  col:"+be.col);
        //}
        if(r<=11 && r>=0 && c>=0 && c<13 && level.bubbles[r][c]!=null) {
            /*
            boolean found = false;
            for(Bubble b : level.checked) {
                //System.out.println("comparing row:"+b.row+"  col:"+b.col);
                if(level.bubbles[r][c].row==b.row && level.bubbles[r][c].col==b.col) {
                    found = true;
                    //System.out.println("found row:"+r+"  col:"+c);
                    break;
                }
            }
            if(!found) return true;
            else return false;
            */
            if(level.checked.contains(level.bubbles[r][c])) return false;
            else return true;
        }
        else return false;
    }

    public boolean reachFirstRow(Level level) {
        level.checked.clear();
        //System.out.println("CHECKING FALLING FOR BUBBLE "+((Bubble)this).color+"  row:"+((Bubble)this).row+"  col:"+((Bubble)this).col);
        ArrayList<Bubble> around = giveAroundAllColors(level, ((Bubble)this).row, ((Bubble)this).col);
        addToCheckedList(level, (Bubble) this);
        for(Bubble be : around) {
            be.exp(level, true);
        }
        for(Bubble b : level.checked) {
            //System.out.println("Checked row:"+b.row+"  col:"+b.col+"   for bubble r:"+((Bubble)this).row+"  c:"+((Bubble)this).col);
            if(b.row==0) {
                return true;
            }
        }
        return false;
    }

>>>>>>> origin/gian
    public abstract void render(SpriteBatch batch);

    public Rectangle getCollisionRect(float x, float y) {
        return new Rectangle(x, y, width, height);
    }
}

