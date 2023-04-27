package edu.aau.g404.core;

import edu.aau.g404.api.hue.HueLight;
import edu.aau.g404.device.LightController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// TODO: Work in progress
public final class Automation  {
    private LightController lightController;
    private ExecutorService executorService;
    private HueLight newState;
    private HueLight alternativeState;

    public Automation(LightController lightController) {
        this.lightController = lightController;
        this.executorService = Executors.newSingleThreadExecutor();

        this.newState = new HueLight();
        newState.setColor(255, 0, 0).isOn(true).setBrightness(100); // Test

        this.alternativeState = new HueLight();
        alternativeState.setColor(0, 0, 255).isOn(true).setBrightness(100); // Test
    }

    public void start(String identifier, long intervalMillis, int numberOfCycles) {
        Runnable runnable = () -> {
            for (int i = 0; i < numberOfCycles; i++) {
                lightController.updateLightState(identifier, newState);
                sleep(intervalMillis);
                lightController.updateLightState(identifier, alternativeState);
                sleep(intervalMillis);
            }
        };

        executorService.submit(runnable);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        executorService.shutdown();
    }
}