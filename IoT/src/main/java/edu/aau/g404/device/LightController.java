package edu.aau.g404.device;

public interface LightController {
    void updateLightState(String identifier, SmartLight light);
    SmartLight getLightClass();
}
