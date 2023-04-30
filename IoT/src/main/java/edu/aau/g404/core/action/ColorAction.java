package edu.aau.g404.core.action;

import edu.aau.g404.device.LightController;
import edu.aau.g404.device.SmartLight;

// TODO: Work in progress
public final class ColorAction implements Action {
    private int red, green, blue;
    private SmartLight smartLight;

    public ColorAction(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void execute(LightController lightController, String identifier) {
        try {
            smartLight = lightController.getLightClass().getClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        smartLight.setColor(red, green, blue);
        lightController.updateLightState(identifier, smartLight);
    }

}
