package edu.aau.g404.device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public interface SmartLight extends SmartDevice {
    SmartLight isOn(boolean bool);
    SmartLight setBrightness(float brightness);
    SmartLight setColor(int red, int green, int blue);
}