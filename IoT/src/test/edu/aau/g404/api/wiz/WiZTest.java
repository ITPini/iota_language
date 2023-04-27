package edu.aau.g404.api.wiz;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

// TODO: Implement tests
@TestMethodOrder(MethodOrderer.MethodName.class)
class WiZTest {

    private static WiZ wiz;
    private static WiZLight testLight;

    @BeforeAll
    static void beforeAll() {
        testLight = new WiZLight();
        testLight.setBrightness(100).setColor(0, 255, 255).isOn(true);

        wiz = new WiZ();

        wiz.updateLightState("192.168.0.107", testLight);
    }

    @Test
    void updateLightState() {
    }
}