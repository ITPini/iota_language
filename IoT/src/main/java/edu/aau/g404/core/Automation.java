package edu.aau.g404.core;

import edu.aau.g404.core.action.Action;
import edu.aau.g404.core.trigger.Trigger;
import edu.aau.g404.device.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Automation class represents a system to manage and execute a series of actions
 * based on a series of triggers for various light controllers.
 */
public final class Automation<T extends Action> {
    private ScheduledExecutorService executorService;
    private List<AutomationThread> automationThreads = new ArrayList<>();

    /**
     * Construct a ScheduledExecutorService that uses the available number of processors.
     */
    public Automation() {
        this.executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()+30);
    }

    /**
     * Adds a new AutomationThread to the list of automationThreads
     * with the provided controller, identifier, actionList and triggerList.
     * @param controller        The controller responsible for managing the SmartDevice.
     * @param identifier        The identifier of the SmartLight.
     * @param actionList        List of actions to be executed.
     * @param triggerList       List of trigger to be checked.
     */
    public void addThread(Controller controller, String identifier, List<T> actionList, List<Trigger> triggerList) {
        automationThreads.add(new AutomationThread(controller, identifier, actionList, triggerList));
    }

    /**
     * Starts the Automation by scheduling the automationThreads to run at a fixed rate.
     */
    public void start(){
        for (AutomationThread thread : automationThreads) {
            executorService.scheduleAtFixedRate(thread::run, 1, 1000, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Stops the Automation by shutting down the executorService and waiting for the tasks to complete.
     * Just for unit testing currently.
     */
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

    /**
     * AutomationThread is a private inner class that represents an individual automation thread
     * with a specific controller, identifier, actionList and triggerList.
     */
    private class AutomationThread {
        private Controller controller;
        private String identifier;
        private List<T> actionList;
        private List<Trigger> triggerList;
        private long lastExecutionTime;

        public AutomationThread(Controller controller, String identifier, List<T> actionList, List<Trigger> triggerList) {
            this.controller = controller;
            this.identifier = identifier;
            this.actionList = actionList;
            this.triggerList = triggerList;
        }

        /**
         * Runs the AutomationThread by checking if the actions should be triggered
         * and executing them if the conditions are met.
         */
        public void run() {
            long currentTime = System.currentTimeMillis();

            // Check if enough time has passed since the last execution (1 minute)
            boolean isTimeToExecute = (currentTime - lastExecutionTime) >= TimeUnit.MINUTES.toMillis(1);

            // If enough time has passed and any triggers in the trigger list are activated
            if (isTimeToExecute && shouldTrigger()) {
                for (Action lightAction : actionList) {
                    sleep(300*automationThreads.indexOf(this));
                    lightAction.execute(controller, identifier);
                    sleep(1000);
                }
                // Update the last execution time to the current time
                lastExecutionTime = currentTime;
            }
        }

        /**
         * Check if any of the triggers in the triggerList are triggered.
         * @return {@code true} if any of the triggers are triggered, {@code false} otherwise.
         */
        private boolean shouldTrigger(){
            for (Trigger trigger : triggerList) {
                if (trigger.isTriggered()) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Sleeps the current thread for the specified number of milliseconds.
         * @param millis    The number of milliseconds the current thread should sleep.
         */
        private void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}