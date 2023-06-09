package edu.aau.g404.api.wiz;

import edu.aau.g404.device.light.SmartLight;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO: Implement tests
/**
 * This unit tests only works if the client and a WiZ Light is connected to the same network.
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
class WiZControllerTest {

    private static WiZController wiZController;
    private static WiZLight testLight;

    @BeforeAll
    static void beforeAll() {
        testLight = new WiZLight();
        testLight.setBrightness(100).setColors(0, 255, 255).changeOnState(true);

        wiZController = new WiZController();

        wiZController.updateLightState("192.168.0.107", testLight);
    }

    @Test
    void updateLightState() {
    }

    @Test
    void getLightClass() {
        SmartLight smartLight = wiZController.getLightClass();
        assertEquals(WiZLight.class, smartLight.getClass());
    }
}