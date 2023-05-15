package edu.aau.g404.core.action.light;

import edu.aau.g404.device.Controller;
import edu.aau.g404.device.light.LightController;
import edu.aau.g404.device.light.SmartLight;

/**
 * OnAction class represents an action that turns a SmartLight on or off.
 */
public final class OnAction implements LightAction {
    private boolean on;

    public OnAction(boolean on) {
        this.on = on;
    }

    public OnAction() {

    }

    /**
     * Executes the OnAction by creating a new SmartLight instance with the specified on/off state
     * and updating the light state using the provided LightController and identifier.
     * @param controller        The LightController implementation for the SmartLight.
     * @param identifier        The unique identifier of the SmartLight to be updated.
     */
    @Override
    public void execute(Controller controller, String identifier) {
        LightController lightController = (LightController) controller;
        SmartLight newLightState = createSmartLightInstance(lightController);
        newLightState.changeOnState(on);
        lightController.updateLightState(identifier, newLightState);
    }

    @Override
    public String toString() {
        return "OnAction{" +
                "on=" + on +
                '}';
    }
}