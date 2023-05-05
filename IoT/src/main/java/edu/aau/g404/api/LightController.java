package edu.aau.g404.api;

import edu.aau.g404.device.SmartLight;

public interface LightController {
    void updateLightState(String identifier, SmartLight newLightState);
    SmartLight getLightClass();
}
