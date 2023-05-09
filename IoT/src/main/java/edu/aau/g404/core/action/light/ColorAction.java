package edu.aau.g404.core.action.light;

import edu.aau.g404.device.Controller;
import edu.aau.g404.device.light.LightController;
import edu.aau.g404.device.light.SmartLight;

/**
 * ColorAction class represents an action that sets the color of a SmartLight.
 */
public final class ColorAction implements LightAction {
    private int red, green, blue;

    public ColorAction(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Executes the ColorAction by creating a new SmartLight instance with the specified color
     * and updating the light state using the provided LightController and identifier.
     * @param controller        The controller responsible for managing the smart light.
     * @param identifier        The unique identifier of the SmartLight to be updated.
     */
    @Override
    public void execute(Controller controller, String identifier) {
        LightController lightController = (LightController) controller;
        SmartLight newLightState = createSmartLightInstance(lightController);
        newLightState.setColor(red, green, blue);
        lightController.updateLightState(identifier, newLightState);
    }
}