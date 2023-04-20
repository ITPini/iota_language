package edu.aau.g404.device;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class LightTest {
    private static Light testLight;

    @BeforeAll
    static void beforeAll() {
        Light.MetaData metaData = new Light.MetaData().setName("Test Light");
        Light.On onState = new Light.On().setOn(true);
        Light.Dimming dimming = new Light.Dimming().setBrightness(50.0f);
        Light.Color color = new Light.Color().setX(0.4).setY(0.6);

        testLight = new Light()
                .setMetaData(metaData)
                .setOnState(onState)
                .setDimming(dimming)
                .setColor(color);
    }

    @Test
    void getIdentifier() {
        // TODO: Implement better test
        assertNull(testLight.getIdentifier());
    }

    @Test
    void getMetaData() {
        assertEquals("Test Light", testLight.getMetaData().getName());
    }

    @Test
    void setMetaData() {
        Light.MetaData newMetaData = new Light.MetaData().setName("New Test Light");
        testLight.setMetaData(newMetaData);
        assertEquals("New Test Light", testLight.getMetaData().getName());
    }

    @Test
    void getOnState() {
        assertTrue(testLight.getOnState().isOn());
    }

    @Test
    void setOnState() {
        Light.On newState = new Light.On().setOn(false);
        testLight.setOnState(newState);
        assertFalse(testLight.getOnState().isOn());
    }

    @Test
    void getDimming() {
        assertEquals(50.0f, testLight.getDimming().getBrightness());
    }

    @Test
    void setDimming() {
        Light.Dimming newDimming = new Light.Dimming().setBrightness(75.0f);
        testLight.setDimming(newDimming);
        assertEquals(75.0f, testLight.getDimming().getBrightness());
    }

    @Test
    void getColor() {
        assertEquals(0.4, testLight.getColor().getX());
        assertEquals(0.6, testLight.getColor().getY());
    }

    @Test
    void setColor() {
        Light.Color newColor = new Light.Color(0.5, 0.7);
        testLight.setColor(newColor);
        assertEquals(0.5, testLight.getColor().getX());
        assertEquals(0.7, testLight.getColor().getY());
    }
}