package edu.aau.g404.protocol.https;

import edu.aau.g404.device.Light;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class PUTTest {

    private static final PUT put = new PUT();

    @BeforeAll
    static void beforeAll() {
        put
                .setUrl("https://192.168.0.134/clip/v2/resource/light/ac37190b-eafe-4181-a6d6-89a938714b73")
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

        Light light = new Light();
        light.setOnState(new Light.On(true));
        light.setDimming(new Light.Dimming(100.0f));
        Response response = put.request(light);
        assertEquals(200, response.getResponseCode());
    }

    @Test
    void getUrl() {
        assertEquals("https://192.168.0.134/clip/v2/resource/light/ac37190b-eafe-4181-a6d6-89a938714b73", put.getUrl());
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