package edu.aau.g404.api.hue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class HueLightTest {
    private static HueLight testLight;

    @BeforeAll
    static void beforeAll() {
        HueLight.MetaData metaData = new HueLight.MetaData().setName("Test Light");
        HueLight.On onState = new HueLight.On().setOn(true);
        HueLight.Dimming dimming = new HueLight.Dimming().setBrightness(50.0f);
        HueLight.Color color = new HueLight.Color().setX(0.4).setY(0.6);

        testLight = new HueLight()
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
        HueLight.MetaData newMetaData = new HueLight.MetaData().setName("New Test Light");
        testLight.setMetaData(newMetaData);
        assertEquals("New Test Light", testLight.getMetaData().getName());
    }

    @Test
    void getOnState() {
        assertTrue(testLight.getOnState().isOn());
    }

    @Test
    void setOnState() {
        HueLight.On newState = new HueLight.On().setOn(false);
        testLight.setOnState(newState);
        assertFalse(testLight.getOnState().isOn());
    }

    @Test
    void getDimming() {
        assertEquals(50.0f, testLight.getDimming().getBrightness());
    }

    @Test
    void setDimming() {
        HueLight.Dimming newDimming = new HueLight.Dimming().setBrightness(75.0f);
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
        HueLight.Color newColor = new HueLight.Color(0.5, 0.7);
        testLight.setColor(newColor);
        assertEquals(0.5, testLight.getColor().getX());
        assertEquals(0.7, testLight.getColor().getY());
    }
}