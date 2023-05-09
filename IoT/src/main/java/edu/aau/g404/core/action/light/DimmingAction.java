package edu.aau.g404.core.action.light;

import edu.aau.g404.device.Controller;
import edu.aau.g404.device.light.LightController;
import edu.aau.g404.device.light.SmartLight;

/**
 * DimmingAction class represents an action that sets the brightness of a SmartLight.
 */
public final class DimmingAction implements LightAction {
    private float brightness;

    public DimmingAction(float brightness) {
        this.brightness = brightness;
    }

    /**
     * Executes the DimmingAction by creating a new SmartLight instance with the specified brightness
     * and updating the light state using the provided LightController and identifier.
     * @param controller        The controller responsible for managing the smart light.
     * @param identifier        The unique identifier of the SmartLight to be updated.
     */
    @Override
    public void execute(Controller controller, String identifier) {
        LightController lightController = (LightController) controller;
        SmartLight newLightState = createSmartLightInstance(lightController);
        newLightState.setBrightness(brightness);
        lightController.updateLightState(identifier, newLightState);
    }
}