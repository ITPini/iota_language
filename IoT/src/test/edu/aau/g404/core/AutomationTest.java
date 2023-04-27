package edu.aau.g404.core;

import edu.aau.g404.api.hue.Hue;
import edu.aau.g404.device.LightController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// TODO: Implement tests
class AutomationTest {

    LightController controller = new Hue("192.168.0.134", "XAxUnLEodCpkcqb0hnLYi--mdL0x4J3MbQZZ5iuc");
    Automation automation = new Automation(controller);

    @Test
    void start() {
        automation.start("b20b77ab-7e6f-4fd9-bc56-96fd23d0358c", 10000, 2);
        automation.stop();
    }
}