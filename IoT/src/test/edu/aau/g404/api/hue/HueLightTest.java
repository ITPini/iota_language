package edu.aau.g404.api.hue;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class HueLightTest {
    private HueLight testLight;

    @BeforeEach
    void init() {
        testLight = new HueLight();
    }

    @Test
    void rgbToXY() {
        double[] xy = testLight.rgbToXY(255, 100, 12);
        assertEquals(0.64180971468, xy[0], 0.0001);
        assertEquals(0.34607322094, xy[1], 0.0001);
    }

    @Test
    void toLinearRGB() {
        assertEquals(1, testLight.toLinearRGB(255));
        assertEquals(0.12743768043, testLight.toLinearRGB(100), 0.0001);
        assertEquals(0.00367650732, testLight.toLinearRGB(12), 0.0001);
    }

    @Test
    void isOn() {
        testLight.isOn(true);
        assertTrue(testLight.getOnState().isOn());
    }

    @Test
    void setBrightness() {
        testLight.setBrightness(0.64f);
        assertEquals(0.64f, testLight.getDimming().getBrightness());
    }

    @Test
    void setColor() {
        testLight.setColor(255, 100, 12);
        double[] xy = testLight.rgbToXY(255, 100, 12);
        assertEquals(xy[0], testLight.getColor().getX(), 0.0001);
        assertEquals(xy[1], testLight.getColor().getY(), 0.0001);
    }

    @Test
    void gettersAndSetters() {
        HueLight.MetaData metaData = new HueLight.MetaData().setName("TestName");
        HueLight.On onState = new HueLight.On(true);
        HueLight.Dimming dimming = new HueLight.Dimming(0.75f);
        HueLight.Color color = new HueLight.Color(0.64180971468, 0.34607322094);

        testLight.setMetaData(metaData)
                .setOnState(onState)
                .setDimming(dimming)
                .setColor(color);

        assertEquals(metaData, testLight.getMetaData());
        assertEquals(onState, testLight.getOnState());
        assertEquals(dimming, testLight.getDimming());
        assertEquals(color, testLight.getColor());
    }
}