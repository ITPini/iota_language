package edu.aau.g404.api.wiz;

import edu.aau.g404.device.light.LightController;
import edu.aau.g404.device.light.SmartLight;
import edu.aau.g404.protocol.udp.UdpRequest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

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
    public SmartLight getLightState(String identifier) {
        return null;// TODO: Implement this
    }

    @Override
    public SmartLight getLightClass() {
        return new WiZLight();
    }

    @Override
    public void printDevices() {
        String payload = "{\"method\":\"getPilot\",\"params\":{}}";
        byte[] payloadBytes = payload.getBytes();
        byte[] buffer = new byte[1024];
        DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);

            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
            DatagramPacket packet = new DatagramPacket(payloadBytes, payloadBytes.length, broadcastAddress, DEFAULT_WIZ_PORT);

            socket.send(packet);

            socket.setSoTimeout(1000);
            System.out.println("\u001b[33mList of WiZ Devices Found:\u001b[0m");
            while (true) {
                socket.receive(responsePacket);
                System.out.printf("\u001B[32m%s\u001b[36m\n", responsePacket.getAddress().getHostAddress());
            }
        } catch (SocketTimeoutException e) {
            if (responsePacket.getAddress() == null) {
                System.out.println("\u001B[31mNo WiZ devices found.\u001b[0m");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}