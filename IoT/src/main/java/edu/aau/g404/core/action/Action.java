package edu.aau.g404.core.action;

import edu.aau.g404.api.LightController;
import edu.aau.g404.device.SmartLight;

/**
 * Action interface for defining the actions to be performed on IoT devices.
 * Provides methods to execute an action and create a SmartDevice instance.
 * Currently, only works for SmartLight objects!
 */
public interface Action {
    /**
     * Execute the action on the SmartLight with the specified identifier.
     * @param lightController   The LightController implementation for the SmartLight.
     * @param identifier        The unique identifier of the SmartLight to be updated.
     */
    void execute(LightController lightController, String identifier);

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