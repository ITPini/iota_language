package edu.aau.g404.device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SmartLight {
    // private String identifier; // TODO: Implement this

    public SmartLight() {
    }

    public abstract SmartLight isOn(boolean bool);
    public abstract SmartLight setBrightness(float brightness);
    public abstract SmartLight setColor(int red, int green, int blue);
}






