package com.gatedev.bobble.input;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gatedev.bobble.Assets;
import com.gatedev.bobble.input.button.GameButton;
import com.gatedev.bobble.screen.GameScreen;

public class Controls {
	private GameButton leftButton;
    private GameButton rightButton;
    private GameButton shootButton;
	private GameButton pauseButton;
	private Keyboard keyboard;
	
	public Controls(Keyboard keyboard) {
		this.keyboard = keyboard;
		loadControls();
	}

    private void loadControls() {
        leftButton = new GameButton(-200, -365, 50, 35, Assets.leftButton);
        rightButton = new GameButton(-120, -365, 50, 35, Assets.rightButton);
        shootButton = new GameButton(135, -375, 60, 60, Assets.shootButton);
        pauseButton = new GameButton(165, 360, 37, 29, Assets.pauseButton);
    }
	
	public void tick() {
		if(!GameScreen.pause && Gdx.app.getType()== Application.ApplicationType.Android) {
            leftButton.tick();
            rightButton.tick();
            shootButton.tick();
            pauseButton.tick();
            keyboard.left.nextState = leftButton.getInfo();
            keyboard.right.nextState = rightButton.getInfo();
            keyboard.shoot.nextState = shootButton.getInfo();
			keyboard.pause.nextState = pauseButton.getInfo();
		}
	}
	
	public void render(SpriteBatch batch) {
        leftButton.render(batch);
        rightButton.render(batch);
        shootButton.render(batch);
		pauseButton.render(batch);
	}
}
