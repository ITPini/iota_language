package edu.aau.g404.core;

import edu.aau.g404.core.action.Action;
import edu.aau.g404.device.LightController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Automation  {
    private ExecutorService executorService;
    private List<Action> actionList = new ArrayList<>();

    public Automation() {
        this.executorService = Executors.newCachedThreadPool();
    }

    public void addAction(Action action) {
        actionList.add(action);
    }

    public void start(LightController lightController, String identifier, long intervalMillis, int numberOfCycles) {
        Runnable runnable = () -> {
            for (int i = 0; i < numberOfCycles; i++) {
                for (Action action : actionList) {
                    action.execute(lightController, identifier);
                    sleep(intervalMillis);
                }
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
        try {
            if (!executorService.awaitTermination(5, TimeUnit.MINUTES)) { // Wait for the tasks to complete, with a specified timeout (e.g., 5 minutes)
                System.out.println("Some tasks did not complete within the specified timeout.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while waiting for tasks to complete.", e);
        }
    }
}