package edu.aau.g404.api.hue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class HueLightTest {
    private static HueLight testLight = new HueLight();

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
}