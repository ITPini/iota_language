package edu.aau.g404.core.trigger;

import java.time.LocalTime;

public final class TimeTrigger implements Trigger {
    private LocalTime triggerTime;

    public TimeTrigger(LocalTime triggerTime) {
        this.triggerTime = triggerTime;
    }
    @Override
    public boolean isTriggered() {
        return LocalTime.now().isAfter(triggerTime.minusMinutes(1)) && LocalTime.now().isBefore(triggerTime.plusMinutes(1));
    }
}
