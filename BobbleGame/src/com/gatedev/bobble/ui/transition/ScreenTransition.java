package com.gatedev.bobble.ui.transition;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gatedev.bobble.screen.BaseScreen;
import com.gatedev.bobble.ui.NotificationManager;
import com.gatedev.bobble.ui.graphics.ImmediateModeRendererUtils;

import java.util.ArrayList;

/**
 * User: Gianluca
 * Date: 31/05/13
 * Time: 17.43
 */
public class ScreenTransition extends BaseScreen {

    protected static SpriteBatch batche;

    public static class TransitionEffect {

        protected TimeTransition timeTransition;

        protected float getAlpha() {
            return timeTransition.get();
        }

        public TransitionEffect(float duration) {
            timeTransition = new TimeTransition();
            timeTransition.start(duration);
        }

        public void update(float delta, int who) {
            timeTransition.update(delta, who);
        }

        public void render(BaseScreen current, BaseScreen next, float delta) {

        }

        public boolean isFinished() {
            return timeTransition.isFinished();
        }

    }

    public static class FadeOutTransitionEffect extends TransitionEffect {

        Color color = new Color();

        public FadeOutTransitionEffect(float duration) {
            super(duration);
        }

        @Override
        public void render(BaseScreen current, BaseScreen next, float delta) {
            super.update(delta, 0);
            current.render(delta);
            color.set(1f, 1f, 1f, getAlpha());

            Gdx.gl10.glEnable(GL10.GL_BLEND);
            ImmediateModeRendererUtils.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            ImmediateModeRendererUtils.fillRectangle(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), color);
            Gdx.gl10.glDisable(GL10.GL_BLEND);
        }

    }

    public static class FadeInTransitionEffect extends TransitionEffect {

        Color color = new Color();

        public FadeInTransitionEffect(float duration) {
            super(duration);
        }

        @Override
        public void render(BaseScreen current, BaseScreen next, float delta) {
            super.update(delta, 1);

            next.render(delta);
            color.set(1f, 1f, 1f, 1-getAlpha());

            Gdx.gl10.glEnable(GL10.GL_BLEND);
            ImmediateModeRendererUtils.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            ImmediateModeRendererUtils.fillRectangle(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), color);
            Gdx.gl10.glDisable(GL10.GL_BLEND);
        }

    }

    BaseScreen current;
    BaseScreen next;
    Game game;

    int currentTransitionEffect;
    ArrayList<TransitionEffect> transitionEffects;

    public ScreenTransition(Game game, BaseScreen current, BaseScreen next, ArrayList<TransitionEffect> transitionEffects, NotificationManager notificationManager, SpriteBatch batch) {
        super(null, notificationManager);
        this.current = current;
        this.game = game;
        this.next = next;
        this.transitionEffects = transitionEffects;
        this.currentTransitionEffect = 0;
        batche = batch;
    }

    public void init() {

    }

    public void update(float delta) {

        if (currentTransitionEffect >= transitionEffects.size()) {
            onTransitionFinished();
            return;
        }

        //transitionEffects.get(currentTransitionEffect).update(delta, 0);
        if (transitionEffects.get(currentTransitionEffect).isFinished()) {
            currentTransitionEffect++;
        }

    }

    protected void onTransitionFinished() {
        //dispose();
        game.setScreen(next);
    }

    @Override
    public void renderScreen() {
        update(Gdx.graphics.getDeltaTime());
        if (currentTransitionEffect >= transitionEffects.size()) {
            return;
        }
        transitionEffects.get(currentTransitionEffect).render(current, next, Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {

    }

}
