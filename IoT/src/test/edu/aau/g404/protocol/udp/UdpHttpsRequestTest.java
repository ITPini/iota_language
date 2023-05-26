package edu.aau.g404.protocol.udp;

import edu.aau.g404.api.wiz.WiZLight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
// TODO: Implement tests

@TestMethodOrder(MethodOrderer.MethodName.class)
class UdpHttpsRequestTest {

    private static final UdpRequest udp = new UdpRequest();

    @BeforeEach
    void init() {
        udp.setIp("192.168.0.207").setPort(38899);
    }

    @Test
    void request() {
        WiZLight light = new WiZLight();
        light.setBrightness(50);
        udp.request(light);
    }
}