package edu.aau.g404.protocol.https;

import edu.aau.g404.api.hue.HueLight;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class HttpsPutRequestTest {
    private static final HttpsPutRequest HTTPS_PUT_REQUEST = new HttpsPutRequest();

    @BeforeEach
    void init() {
        HTTPS_PUT_REQUEST
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

        HueLight light = new HueLight();
        light.setColor(255, 50, 50).setBrightness(100f).isOn(true);

        HTTPS_PUT_REQUEST.request(light);
    }

    @Test
    void getUrl() {
        assertEquals("https://192.168.0.134/clip/v2/resource/grouped_light/55cd6585-bde1-4983-9e28-0e96d1ed435a", HTTPS_PUT_REQUEST.getUrl());
    }

    @Test
    void setUrl() {
        HTTPS_PUT_REQUEST.setUrl("https://192.168.0.100/clip/v2/resource/light/68b29eda-68fc-499f-a6e7-2ba957aeb1d3");
        assertEquals("https://192.168.0.100/clip/v2/resource/light/68b29eda-68fc-499f-a6e7-2ba957aeb1d3", HTTPS_PUT_REQUEST.getUrl());
    }

    @Test
    void getApplicationKey() {
        assertEquals("XAxUnLEodCpkcqb0hnLYi--mdL0x4J3MbQZZ5iuc", HTTPS_PUT_REQUEST.getApplicationKey());
    }

    @Test
    void setApplicationKey() {
        HTTPS_PUT_REQUEST.setApplicationKey("aYvLvawY7PE2Y4yZ9of1FYkMS7onTlIkWOZfuAex");
        assertEquals("aYvLvawY7PE2Y4yZ9of1FYkMS7onTlIkWOZfuAex", HTTPS_PUT_REQUEST.getApplicationKey());
    }
}