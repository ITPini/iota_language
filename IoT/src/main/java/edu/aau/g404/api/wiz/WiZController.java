package edu.aau.g404.api.wiz;

import edu.aau.g404.device.light.LightController;
import edu.aau.g404.device.light.SmartLight;
import edu.aau.g404.protocol.udp.UdpRequest;

/**
 * Provides methods for controlling and interacting with WiZ smart lights.
 */
public final class WiZController implements LightController {
    private static final int DEFAULT_WIZ_PORT = 38899;
    private UdpRequest udp = new UdpRequest();

    public WiZController(){

    }

    /**
     * Updates the state of a WiZ smart light with the given identifier using the provided newLightState.
     * @param identifier    Local IP address of the WiZ smart light to be updated.
     * @param newLightState The new state of the SmartLight.
     */
    @Override
    public void updateLightState(String identifier, SmartLight newLightState) {
        udp.setIp(identifier).setPort(DEFAULT_WIZ_PORT).request(newLightState);
    }

    @Override
    public SmartLight getLightClass() {
        return new WiZLight();
    }
}