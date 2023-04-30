package edu.aau.g404.core.action;

import edu.aau.g404.device.LightController;

// TODO: Work in progress
public interface Action {
    void execute(LightController lightController, String identifier);
}
