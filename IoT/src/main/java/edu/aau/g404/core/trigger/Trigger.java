package edu.aau.g404.core.trigger;

/**
 * Trigger interface for defining conditions to trigger specific actions.
 * Provides a method to check if the trigger is met.
 */
public interface Trigger {
    /**
     * Determines if the trigger conditions is met.
     * @return {@code true} if the trigger conditions is met, {@code false} otherwise.
     */
    boolean isTriggered();

    String toString();
}