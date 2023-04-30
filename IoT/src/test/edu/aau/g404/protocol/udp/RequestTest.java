package edu.aau.g404.protocol.udp;

import edu.aau.g404.api.wiz.WiZLight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;
// TODO: Implement tests

@TestMethodOrder(MethodOrderer.MethodName.class)
class RequestTest {

    private static final Request udp = new Request();

    @BeforeEach
    void init() {
        udp.setIp("192.168.0.106").setPort(38899);
    }

    @Test
    void request() {
        WiZLight light = new WiZLight(1, "setState", new WiZLight.Params());
        light.setBrightness(50);

        udp.request(light);

        udp.setIp("192.168.0.107");
        udp.request(light);
    }
}