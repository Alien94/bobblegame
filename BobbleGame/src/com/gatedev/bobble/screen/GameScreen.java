package com.gatedev.bobble.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.gatedev.bobble.Renderer;
import com.gatedev.bobble.input.Controls;
import com.gatedev.bobble.input.Keyboard;
import com.gatedev.bobble.input.KeyboardHandler;
import com.gatedev.bobble.level.Level;

import java.util.Random;

/**
 * User: Gianluca
 * Date: 15/07/13
 * Time: 15.35
 */
public class GameScreen implements Screen {

    public Game game;
    public Level level;
    public Renderer renderer;
    public Controls controls;
    public Keyboard keyboard = new Keyboard();
    public static Random random;

    public static boolean pause = false;

    public int curFps;
    private final int UPD = 60;
    private final int FPS = 60;
    private final double TIME_BETWEEN_UPDATES = 1000000000 / UPD;
    private final double TIME_BETWEEN_RENDERS = 1000000000 / FPS;
    private final int MAX_UPDATES_BEFORE_RENDER = 1;
    private double lastUpdateTime = System.nanoTime();
    private double lastRenderTime = System.nanoTime();
    private int lastSecondTime = (int) (lastUpdateTime / 1000000000);
    private long lastTimer1 = System.currentTimeMillis();
    private int frames = 0;

    public GameScreen(Game game) {
        this.game = game;
        GameScreen.random = new Random();
        this.renderer = new Renderer(this);
        this.level = new Level(this);
        this.controls = new Controls(keyboard);
        Gdx.input.setInputProcessor(new KeyboardHandler(keyboard, level.arrow));
        //level.entities.add(new Bubble(100, 100));
    }

    private void tick() {
        frames++;
        if (System.currentTimeMillis() - lastTimer1 > 1000) {
            lastTimer1 += 1000;
            curFps = frames;
            frames = 0;
        }
        keyboard.tick();
        controls.tick();
        level.tick();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 0);

        Renderer.camera.apply(Gdx.gl10);
        Renderer.camera.update();

        double now = System.nanoTime();
        int updateCount = 0;
        while(now-lastUpdateTime>TIME_BETWEEN_UPDATES && updateCount<MAX_UPDATES_BEFORE_RENDER) {
            tick();
            lastUpdateTime += TIME_BETWEEN_UPDATES;
            updateCount++;
        }
        if(now-lastUpdateTime>TIME_BETWEEN_UPDATES) {
            lastUpdateTime = now - TIME_BETWEEN_UPDATES;
        }
        renderer.render();
        lastRenderTime = now;
        int thisSecond = (int) (lastUpdateTime / 1000000000);
        if(thisSecond > lastSecondTime) {
            lastSecondTime = thisSecond;
        }
        while(now-lastRenderTime<TIME_BETWEEN_RENDERS && now-lastUpdateTime<TIME_BETWEEN_UPDATES) {
            Thread.yield();
            try {
                Thread.sleep(1);
            } catch(Exception e) {}
            now = System.nanoTime();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
