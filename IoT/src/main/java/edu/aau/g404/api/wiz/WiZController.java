package edu.aau.g404.api.wiz;

import edu.aau.g404.device.LightController;
import edu.aau.g404.device.SmartLight;
import edu.aau.g404.protocol.udp.UdpRequest;

public final class WiZController implements LightController {
    private static final int DEFAULT_WIZ_PORT = 38899;
    private UdpRequest udp = new UdpRequest();

    public WiZController(){

    }

    @Override
    public void updateLightState(String identifier, SmartLight light) {
        udp.setIp(identifier).setPort(DEFAULT_WIZ_PORT).request(light);
    }

    @Override
    public SmartLight getLightClass() {
        return new WiZLight();
    }
}