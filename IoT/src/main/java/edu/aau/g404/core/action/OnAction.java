package edu.aau.g404.core.action;

import edu.aau.g404.api.LightController;
import edu.aau.g404.device.SmartLight;

public final class OnAction implements Action {
    private boolean on;

    public OnAction(boolean on) {
        this.on = on;
    }

    @Override
    public void execute(LightController lightController, String identifier) {
        SmartLight newLightState = createSmartLightInstance(lightController);
        newLightState.isOn(on);
        lightController.updateLightState(identifier, newLightState);
    }
}
