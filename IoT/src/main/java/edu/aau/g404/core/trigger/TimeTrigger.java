package edu.aau.g404.core.trigger;

import java.time.LocalTime;

/**
 * TimeTrigger class represents a time-based trigger.
 * Implements the Trigger interface and checks if the current time is within a minute of the specified trigger time.
 */
public final class TimeTrigger implements Trigger {
    private LocalTime triggerTime;

    public TimeTrigger(LocalTime triggerTime) {
        this.triggerTime = triggerTime;
    }

    /**
     * Determines if the trigger conditions is met.
     * @return {@code true} if the current time is within a minute of the trigger time, {@code false} otherwise.
     */
    @Override
    public boolean isTriggered() {
        return LocalTime.now().isAfter(triggerTime) && LocalTime.now().isBefore(triggerTime.plusSeconds(1));
    }

    @Override
    public String toString() {
        return "TimeTrigger{" +
                "triggerTime=" + triggerTime +
                '}';
    }
}