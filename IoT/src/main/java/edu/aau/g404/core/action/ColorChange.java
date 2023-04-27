package edu.aau.g404.core.action;

import edu.aau.g404.device.SmartLight;

// TODO: Work in progress
public final class ColorChange implements Action {
    private int red, green, blue;
    private long interval; // Long for Thread.sleep

    public ColorChange(int red, int green, int blue, long interval) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.interval = interval;
    }

    @Override
    public void execute(SmartLight smartLight) {
        smartLight.setColor(red, green, blue);
    }

    public long getInterval() {
        return interval;
    }
}
