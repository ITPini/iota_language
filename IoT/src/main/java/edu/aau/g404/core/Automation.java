package edu.aau.g404.core;

import edu.aau.g404.core.action.Action;
import edu.aau.g404.core.trigger.Trigger;
import edu.aau.g404.api.LightController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class Automation  {
    private ScheduledExecutorService executorService;
    private List<AutomationThread> automationThreads = new ArrayList<>();

    public Automation() {
        this.executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void addThread(LightController lightController, String identifier, List<Action> actionList, List<Trigger> triggerList) {
        automationThreads.add(new AutomationThread(lightController, identifier, actionList, triggerList));
    }

    public void start(){
        for (AutomationThread thread : automationThreads) {
            executorService.scheduleAtFixedRate(thread::run, 0, 1000, TimeUnit.MILLISECONDS);
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

    private static class AutomationThread {
        private LightController lightController;
        private String identifier;
        private List<Action> actionList;
        private List<Trigger> triggerList;
        private long lastExecutionTime;

        public AutomationThread(LightController lightController, String identifier, List<Action> actionList, List<Trigger> triggerList) {
            this.lightController = lightController;
            this.identifier = identifier;
            this.actionList = actionList;
            this.triggerList = triggerList;
        }

        public void run() {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastExecutionTime >= TimeUnit.MINUTES.toMillis(1)) {
                if (shouldTrigger()) {
                    for (Action action : actionList) {
                        action.execute(lightController, identifier);
                        sleep(1000);
                    }
                    lastExecutionTime = currentTime;
                }
            }
        }

        private boolean shouldTrigger(){
            for (Trigger trigger : triggerList) {
                if (trigger.isTriggered()) {
                    return true;
                }
            }
            return false;
        }

        private void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}