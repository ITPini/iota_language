package edu.aau.g404.core.action;

import edu.aau.g404.api.LightController;
import edu.aau.g404.device.SmartLight;

public final class DimmingAction implements Action {
    private float brightness;

    public DimmingAction(float brightness) {
        this.brightness = brightness;
    }

    @Override
    public void execute(LightController lightController, String identifier) {
        SmartLight newLightState = createSmartLightInstance(lightController);
        newLightState.setBrightness(brightness);
        lightController.updateLightState(identifier, newLightState);
    }
}
