package edu.aau.g404.api.wiz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WiZLightTest {
    private WiZLight wiZLight;
    @BeforeEach
    void init() {
        wiZLight = new WiZLight();
    }

    // Interface specific tests
    @Test
    void setOn() {
        wiZLight.changeOnState(true);
        assertTrue(wiZLight.getParams().getState());
    }
    @Test
    void setBrightness() {
        wiZLight.setBrightness(50);
        assertEquals(50, wiZLight.getParams().getDimming());
    }

    @Test
    void setColor() {
        wiZLight.setColors(12, 100, 50);
        assertEquals(12, wiZLight.getParams().getR());
        assertEquals(100, wiZLight.getParams().getG());
        assertEquals(50, wiZLight.getParams().getB());
    }

    // Class specific tests
    @Test
    void gettersAndSetters() {
        wiZLight.setId(2);
        wiZLight.setMethod("setNewState");
        WiZLight.Params params = new WiZLight.Params();
        wiZLight.setParams(params);

        assertEquals(2, wiZLight.getId());
        assertEquals("setNewState", wiZLight.getMethod());
        assertEquals(params, wiZLight.getParams());

        params.setR(255);
        params.setG(100);
        params.setB(50);
        params.setDimming(75);
        params.setState(false);

        assertEquals(255, params.getR());
        assertEquals(100, params.getG());
        assertEquals(50, params.getB());
        assertEquals(75, params.getDimming());
        assertFalse(params.getState());
    }
}