package edu.aau.g404.api.hue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

// TODO: Implement tests
@TestMethodOrder(MethodOrderer.MethodName.class)
class HueTest {

    private static Hue hue;
    private static HueLight testLight;

    @BeforeAll
    static void beforeAll() {
        testLight = new HueLight();
        testLight.setBrightness(100).setColor(0, 0, 255).isOn(true);

        hue = new Hue("192.168.0.134", "XAxUnLEodCpkcqb0hnLYi--mdL0x4J3MbQZZ5iuc");

        hue.updateLightState("b20b77ab-7e6f-4fd9-bc56-96fd23d0358c", testLight);
    }

    @Test
    void updateLightState() {
    }
}