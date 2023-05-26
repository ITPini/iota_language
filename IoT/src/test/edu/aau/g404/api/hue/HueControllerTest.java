package edu.aau.g404.api.hue;

import edu.aau.g404.device.light.SmartLight;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This unit tests only works if the client and Hue Bridge is connected to the same network.
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
class HueControllerTest {

    private static HueController hueController;
    private static HueLight testLight;

    @BeforeAll
    static void beforeAll() {
        testLight = new HueLight();
        testLight.setBrightness(100).setColors(0, 0, 255).changeOnState(true);

        hueController = new HueController("192.168.0.134", "XAxUnLEodCpkcqb0hnLYi--mdL0x4J3MbQZZ5iuc");
    }

    @Test
    void updateLightState() {
        hueController.updateLightState("b20b77ab-7e6f-4fd9-bc56-96fd23d0358c", testLight);
    }

    @Test
    void getLightClass() {
        SmartLight smartLight = hueController.getLightClass();
        assertEquals(HueLight.class, smartLight.getClass());
    }

    @Test
    void getLightState() {
        HueLight hueLight = (HueLight) hueController.getLightState("b20b77ab-7e6f-4fd9-bc56-96fd23d0358c");
        assertEquals(100, hueLight.getBrightness());
        assertTrue(hueLight.getOnState().isOn());
    }
}