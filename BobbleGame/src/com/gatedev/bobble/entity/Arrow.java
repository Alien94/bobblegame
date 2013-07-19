package com.gatedev.bobble.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gatedev.bobble.Assets;
import com.gatedev.bobble.input.Keyboard;
import com.gatedev.bobble.level.Level;
import com.gatedev.bobble.screen.GameScreen;

/**
 * User: Gianluca
 * Date: 15/07/13
 * Time: 16.56
 */
public class Arrow extends Entity {

    public float degrees = 90, step = 2, bubbleVelocity = 14;
    private float minRotation = 165, maxRotation = 15;
    public int nextShoot = 0;
    public Bubble next;
    private Keyboard keyboard;

    public Arrow(float x, float y, Keyboard keyboard) {
        super(x, y);
        this.keyboard = keyboard;
        createNextBubble();
    }

    public void tick(Level level) {
        if(keyboard.left.isPressed) {
            if(degrees<minRotation) degrees+=step;
        }
        else if(keyboard.right.isPressed) {
            if(degrees>maxRotation) degrees-=step;
        }

        if(keyboard.shoot.isPressed && nextShoot==0) {
            next.velocity.x = (float)Math.cos(Math.toRadians(degrees)) * bubbleVelocity;
            next.velocity.y = (float)Math.sin(Math.toRadians(degrees)) * bubbleVelocity;
            level.entities.add(next);

            createNextBubble();
            nextShoot = 30;
        }
    }

    private void createNextBubble() {
        int nextColor = GameScreen.random.nextInt(4);
        //int nextColor = 1;
        if(nextColor==0) next = new Bubble(x+28, y, Assets.yellowBubble, GameScreen.color.YELLOW);
        else if(nextColor==1) next = new Bubble(x+28, y, Assets.blueBubble, GameScreen.color.BLUE);
        else if(nextColor==2) next = new Bubble(x+28, y, Assets.redBubble, GameScreen.color.RED);
        else if(nextColor==3) next = new Bubble(x+28, y, Assets.greenBubble, GameScreen.color.GREEN);
    }

    public void setDegrees(int angle) {
        if(angle<minRotation && angle>maxRotation) degrees = angle;
        else if(angle>minRotation) degrees = minRotation;
        else if(angle<maxRotation) degrees = maxRotation;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(Assets.arrow, x, y, 75, 32, 150, 64, 1, 1, degrees);
        batch.draw(next.img, x+43, y);
    }
}
