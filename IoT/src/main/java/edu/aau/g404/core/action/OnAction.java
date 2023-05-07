package edu.aau.g404.core.action;

import edu.aau.g404.api.LightController;
import edu.aau.g404.device.SmartLight;

/**
 * OnAction class represents an action that turns a SmartLight on or off.
 */
public final class OnAction implements Action {
    private boolean on;

    public OnAction(boolean on) {
        this.on = on;
    }

    /**
     * Executes the OnAction by creating a new SmartLight instance with the specified on/off state
     * and updating the light state using the provided LightController and identifier.
     * @param lightController   The LightController implementation for the SmartLight.
     * @param identifier        The unique identifier of the SmartLight to be updated.
     */
    @Override
    public void execute(LightController lightController, String identifier) {
        SmartLight newLightState = createSmartLightInstance(lightController);
        newLightState.isOn(on);
        lightController.updateLightState(identifier, newLightState);
    }
}