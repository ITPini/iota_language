package edu.aau.g404.core.action;

import edu.aau.g404.device.Controller;

public interface Action {
    /**
     * Execute the action with the specified identifier.
     * @param controller        The controller implementation for the smart device.
     * @param identifier        The unique identifier of the smart device to be updated.
     */
    void execute(Controller controller, String identifier);
}
