package edu.aau.g404.core.action;

import edu.aau.g404.api.LightController;
import edu.aau.g404.device.SmartLight;

/**
 * DimmingAction class represents an action that sets the brightness of a SmartLight.
 */
public final class DimmingAction implements Action {
    private float brightness;

    public DimmingAction(float brightness) {
        this.brightness = brightness;
    }

    /**
     * Executes the DimmingAction by creating a new SmartLight instance with the specified brightness
     * and updating the light state using the provided LightController and identifier.
     * @param lightController   The LightController responsible for managing the SmartLight.
     * @param identifier        The unique identifier of the SmartLight to be updated.
     */
    @Override
    public void execute(LightController lightController, String identifier) {
        SmartLight newLightState = createSmartLightInstance(lightController);
        newLightState.setBrightness(brightness);
        lightController.updateLightState(identifier, newLightState);
    }
}