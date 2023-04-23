package edu.aau.g404.protocol.https;

import edu.aau.g404.api.hue.HueLight;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class PUTTest {

    private static final PUT put = new PUT();

    @BeforeEach
    void setUp() {
        put
                .setUrl("https://192.168.0.134/clip/v2/resource/grouped_light/55cd6585-bde1-4983-9e28-0e96d1ed435a")
                .setApplicationKey("XAxUnLEodCpkcqb0hnLYi--mdL0x4J3MbQZZ5iuc");
    }

    @Test
    void request() {
        // TODO: Better test implementation
        try {
            SSLHelper.disableSSLVerification();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        HueLight.Color color = new HueLight.Color(0.5, 0.1);

        HueLight light = new HueLight();
        light
                .setColor(color)
                .setOnState(new HueLight.On().setOn(true))
                .setDimming(new HueLight.Dimming().setBrightness(100f));

        Response response = put.request(light);
        assertEquals(200, response.getResponseCode());
    }

    @Test
    void getUrl() {
        assertEquals("https://192.168.0.134/clip/v2/resource/grouped_light/55cd6585-bde1-4983-9e28-0e96d1ed435a", put.getUrl());
    }

    @Test
    void setUrl() {
        put.setUrl("https://192.168.0.100/clip/v2/resource/light/68b29eda-68fc-499f-a6e7-2ba957aeb1d3");
        assertEquals("https://192.168.0.100/clip/v2/resource/light/68b29eda-68fc-499f-a6e7-2ba957aeb1d3", put.getUrl());
    }

    @Test
    void getApplicationKey() {
        assertEquals("XAxUnLEodCpkcqb0hnLYi--mdL0x4J3MbQZZ5iuc", put.getApplicationKey());
    }

    @Test
    void setApplicationKey() {
        put.setApplicationKey("aYvLvawY7PE2Y4yZ9of1FYkMS7onTlIkWOZfuAex");
        assertEquals("aYvLvawY7PE2Y4yZ9of1FYkMS7onTlIkWOZfuAex", put.getApplicationKey());
    }
}