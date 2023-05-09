package edu.aau.g404.core.action.light;

import edu.aau.g404.core.action.Action;
import edu.aau.g404.device.light.LightController;
import edu.aau.g404.device.light.SmartLight;

/**
 * Action interface for defining the actions to be performed on IoT lighting devices.
 * Provides methods to execute an action and create a SmartLight instance.
 */
public interface LightAction extends Action {
    /**
     * Create a new SmartLight instance using the LightController implementation.
     * @param lightController   The LightController implementation for the SmartLight.
     * @return                  The new SmartLight instance.
     * @throws RuntimeException If an error occurs during SmartLight instantiation.
     */
    default SmartLight createSmartLightInstance(LightController lightController) {
        try {
            return lightController.getLightClass().getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}