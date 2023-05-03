package edu.aau.g404.core.action;

import edu.aau.g404.device.LightController;
import edu.aau.g404.device.SmartLight;

public interface Action {
    void execute(LightController lightController, String identifier);
    default SmartLight createSmartLightInstance(LightController lightController) {
        try {
            return lightController.getLightClass().getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
