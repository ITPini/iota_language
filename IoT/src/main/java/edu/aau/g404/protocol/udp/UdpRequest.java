package edu.aau.g404.protocol.udp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.aau.g404.device.SmartDevice;

import java.io.IOException;
import java.net.*;

/**
 * UdpRequest class handles sending UDP requests containing a SmartDevice object as payload.
 * @param <T> The type of SmartDevice object to be sent in the UDP request.
 */
public final class UdpRequest<T extends SmartDevice> {
    protected String ip;
    protected int port;

    public UdpRequest() {
    }

    public UdpRequest(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Sends a UDP request with the SmartDevice object as payload.
     * @param newState The SmartDevice object to be sent as payload.
     */
    public void request(T newState) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String payload = objectMapper.writeValueAsString(newState);

            InetAddress address = InetAddress.getByName(ip);
            byte[] data = payload.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

            try (DatagramSocket datagramSocket = new DatagramSocket()) {
                datagramSocket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getIp() {
        return ip;
    }

    public UdpRequest setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public int getPort() {
        return port;
    }

    public UdpRequest setPort(int port) {
        this.port = port;
        return this;
    }
}