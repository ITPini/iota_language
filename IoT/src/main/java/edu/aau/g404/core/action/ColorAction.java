package edu.aau.g404.core.action;

import edu.aau.g404.api.LightController;
import edu.aau.g404.device.SmartLight;

/**
 * ColorAction class represents an action that sets the color of a SmartLight.
 */
public final class ColorAction implements Action {
    private int red, green, blue;

    public ColorAction(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Executes the ColorAction by creating a new SmartLight instance with the specified color
     * and updating the light state using the provided LightController and identifier.
     * @param lightController   The LightController responsible for managing the SmartLight.
     * @param identifier        The unique identifier of the SmartLight to be updated.
     */
    @Override
    public void execute(LightController lightController, String identifier) {
        SmartLight newLightState = createSmartLightInstance(lightController);
        newLightState.setColor(red, green, blue);
        lightController.updateLightState(identifier, newLightState);
    }
}