package com.gatedev.bobble.ui.transition;

/**
 * User: Gianluca
 * Date: 31/05/13
 * Time: 17.45
 */
public class TimeTransition {

    private float transitionTime;
    private float crntTime;

    public boolean finished = true;
    private boolean started = false;

    public boolean isFinished() {
        return finished;
    }

    /**
     * Returns the percentage of the current time over the total time.
     */
    public float get() {
        if (transitionTime == 0)
            return 1f;
        //return Math.round((crntTime/transitionTime)*10000)/10000;
        return crntTime/transitionTime;
    }

    public void start(float time) {
        this.transitionTime = time;
        this.crntTime = 0;
        this.finished = false;
        this.started = true;
    }

    public void stop() {
        this.started = false;
        this.finished = false;
    }

    public void update(float time, int who) {
        if (!started)
            return;
        if (finished)
            return;
        this.crntTime += time;
        if (crntTime >= transitionTime) {
            crntTime = transitionTime;
            finished = true;
        }
    }

}