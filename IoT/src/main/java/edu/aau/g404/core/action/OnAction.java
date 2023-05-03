package edu.aau.g404.core.action;

import edu.aau.g404.device.LightController;
import edu.aau.g404.device.SmartLight;

public final class OnAction implements Action {
    private boolean on;

    public OnAction(boolean on) {
        this.on = on;
    }

    @Override
    public void execute(LightController lightController, String identifier) {
        SmartLight smartLight = createSmartLightInstance(lightController);
        smartLight.isOn(on);
        lightController.updateLightState(identifier, smartLight);
    }
}
