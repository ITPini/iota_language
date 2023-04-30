package edu.aau.g404.api.wiz;

import edu.aau.g404.device.LightController;
import edu.aau.g404.device.SmartLight;
import edu.aau.g404.protocol.udp.Request;

public final class WiZ implements LightController {
    private static final SmartLight LIGHT_CLASS = new WiZLight();
    private static final int DEFAULT_WIZ_PORT = 38899;
    private Request udp;

    public WiZ(){

    }

    @Override
    public void updateLightState(String identifier, SmartLight light) {
        udp = new Request().setIp(identifier).setPort(DEFAULT_WIZ_PORT);
        udp.request(light);
    }

    @Override
    public SmartLight getLightClass() {
        return LIGHT_CLASS;
    }
}
