package com.gatedev.bobble.screen;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gatedev.bobble.input.button.MenuButton;
import com.gatedev.bobble.ui.NotificationManager;
import com.gatedev.bobble.ui.transition.ScreenTransition;

import java.util.ArrayList;

public abstract class BaseScreen implements Screen {

    protected Game game;
    protected OrthographicCamera camera;
    protected SpriteBatch batch;
    protected BaseScreen parent;
    protected Screen gameParent;
    protected Preferences prefs;
    protected BitmapFont primaryFont, secondaryFont;
    protected NotificationManager notificationManager;
    protected boolean slideFinished = false;
    protected ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();

    public BaseScreen(Game game, NotificationManager notificationManager) {
        Gdx.graphics.setVSync(true);
        Gdx.input.setCatchBackKey(true);
        this.game = game;
        this.camera = new OrthographicCamera(480, 320);
        this.batch = new SpriteBatch();
        this.prefs = Gdx.app.getPreferences("BobbleGame");
        this.primaryFont = new BitmapFont(Gdx.files.internal("data/fonts/harabara.fnt"), Gdx.files.internal("data/fonts/harabara.png"), false);
        this.secondaryFont = new BitmapFont(Gdx.files.internal("data/fonts/superretro.fnt"), Gdx.files.internal("data/fonts/superretro.png"), false);
        this.notificationManager = notificationManager;
    }

    public BaseScreen(Game game, BaseScreen parent, NotificationManager notificationManager) {
        this(game, notificationManager);
        this.parent = parent;
    }

    public BaseScreen(Game game, Screen gamePar) {
        this(game, new NotificationManager());
        this.gameParent = gamePar;
    }

    public BaseScreen(Game game) {
        this(game, new NotificationManager());
    }

    protected void changeScreen(BaseScreen next) {
        ArrayList<ScreenTransition.TransitionEffect> effects = new ArrayList<ScreenTransition.TransitionEffect>();

        effects.add(new ScreenTransition.FadeOutTransitionEffect(0.25f));
        effects.add(new ScreenTransition.FadeInTransitionEffect(0.35f));
        ScreenTransition transitionScreen = new ScreenTransition(game, this, next, effects, notificationManager, batch);
        game.setScreen(transitionScreen);
    }

    protected void tick() {
        if(!slideFinished && buttons.size()>0) {
            int finishCount = 0;
            for(MenuButton btn : buttons) {
                btn.tick();
                if(btn.finalX==btn.x) finishCount++;
            }
            if(finishCount==buttons.size()) slideFinished = true;
        }
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //Gdx.gl.glClearColor(0, 0, 0, 0);
        camera.apply(Gdx.gl10);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        tick();
        renderScreen();
        secondaryFont.setColor(Color.WHITE);
        secondaryFont.setScale(0.4f);
        notificationManager.render(batch, secondaryFont);
        batch.end();
    }

    public abstract void renderScreen();

    public void resetSlide() {
        for(MenuButton btn : buttons) {
            btn.x = btn.initX;
        }
        slideFinished = false;
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
