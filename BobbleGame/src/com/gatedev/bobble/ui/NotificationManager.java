package com.gatedev.bobble.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gatedev.bobble.ui.elements.SlideNotification;

import java.util.ArrayList;

/**
 * User: Gianluca
 * Date: 27/05/13
 * Time: 21.18
 */
public class NotificationManager {

    private ArrayList<SlideNotification> slideNotifications = new ArrayList<SlideNotification>();

    public void addSlideNotification(String text) {
        slideNotifications.add(new SlideNotification(text));
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        if(slideNotifications.size()>0) {
            slideNotifications.get(0).render(batch, font);
            if(slideNotifications.get(0).time<=0) slideNotifications.remove(0);
        }
    }
}
