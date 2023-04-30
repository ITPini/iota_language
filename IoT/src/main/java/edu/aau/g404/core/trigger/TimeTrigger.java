package edu.aau.g404.core.trigger;

import java.time.LocalTime;

// TODO: Work in progress
public class TimeTrigger implements Trigger {
    private LocalTime triggerTime;

    public TimeTrigger(LocalTime triggerTime) {
        this.triggerTime = triggerTime;
    }
    @Override
    public boolean isTriggered() {
        return LocalTime.now().isAfter(triggerTime);
    }
}
