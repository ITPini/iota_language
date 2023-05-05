package edu.aau.g404.core.action;

import edu.aau.g404.api.LightController;
import edu.aau.g404.device.SmartLight;

public final class ColorAction implements Action {
    private int red, green, blue;

    public ColorAction(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void execute(LightController lightController, String identifier) {
        SmartLight newLightState = createSmartLightInstance(lightController);
        newLightState.setColor(red, green, blue);
        lightController.updateLightState(identifier, newLightState);
    }

}
