package edu.aau.g404.core;

import edu.aau.g404.api.hue.Hue;
import edu.aau.g404.core.action.Action;
import edu.aau.g404.core.action.ColorAction;
import edu.aau.g404.core.trigger.TimeTrigger;
import edu.aau.g404.core.trigger.Trigger;
import edu.aau.g404.device.LightController;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// TODO: Implement tests
class AutomationTest {

    LightController controller = new Hue("192.168.0.134", "XAxUnLEodCpkcqb0hnLYi--mdL0x4J3MbQZZ5iuc");
    Automation automation = new Automation();

    @Test
    void start() {
        // TODO: Notes to self
        /*
        Hue controller = new Hue("192.168.0.100", "aYvLvawY7PE2Y4yZ9of1FYkMS7onTlIkWOZfuAex");
        Automation automation = new Automation();

        List<Action> actions = new ArrayList<>();
        actions.add(new ColorAction(255,0,0));
        actions.add(new ColorAction(0,255,0));
        actions.add(new ColorAction(0,0,255));

        List<Trigger> triggers = new ArrayList<>();
        triggers.add(new TimeTrigger(LocalTime.of(14, 24)));

        automation.addThread(controller,"b2ef7371-9321-452a-a70e-49ce5b6cd879", actions, triggers);
        automation.addThread(controller,"68b29eda-68fc-499f-a6e7-2ba957aeb1d3", actions, triggers);
        automation.addThread(controller,"fd57ef6d-1c1f-46f4-a7f1-cf2cff1f2cbd", actions, triggers);
         */
    }
}