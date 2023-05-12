package edu.aau.g404.protocol.https;

import edu.aau.g404.api.hue.HueLight;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class HttpsGetRequestTest {
    private static final HttpsGetRequest HTTPS_GET_REQUEST = new HttpsGetRequest();

    @BeforeEach
    void init() {
        HTTPS_GET_REQUEST
                .setUrl("https://192.168.0.134/clip/v2/resource/light")
                .setApplicationKey("XAxUnLEodCpkcqb0hnLYi--mdL0x4J3MbQZZ5iuc");
    }

    @Test
    void request() {
        try {
            SSLHelper.disableSSLVerification();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<HueLight> lights = HTTPS_GET_REQUEST.request();
        assertFalse(lights.isEmpty());

        for (HueLight light : lights) {
            assertNotNull(light);
            assertNotNull(light.getIdentifier());
            assertNotNull(light.getMetaData().getName());
        }
    }

    @Test
    void getUrl() {
        assertEquals("https://192.168.0.134/clip/v2/resource/light", HTTPS_GET_REQUEST.getUrl());
    }

    @Test
    void setUrl() {
        HTTPS_GET_REQUEST.setUrl("https://192.168.0.100/clip/v2/resource/light");
        assertEquals("https://192.168.0.100/clip/v2/resource/light", HTTPS_GET_REQUEST.getUrl());
    }

    @Test
    void getApplicationKey() {
        assertEquals("XAxUnLEodCpkcqb0hnLYi--mdL0x4J3MbQZZ5iuc", HTTPS_GET_REQUEST.getApplicationKey());
    }

    @Test
    void setApplicationKey() {
        HTTPS_GET_REQUEST.setApplicationKey("aYvLvawY7PE2Y4yZ9of1FYkMS7onTlIkWOZfuAex");
        assertEquals("aYvLvawY7PE2Y4yZ9of1FYkMS7onTlIkWOZfuAex", HTTPS_GET_REQUEST.getApplicationKey());
    }
}