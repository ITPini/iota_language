package edu.aau.g404.protocol.udp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.aau.g404.device.SmartDevice;

import java.io.IOException;
import java.net.*;

public final class UdpRequest<T extends SmartDevice> {
    protected String ip;
    protected int port;

    public UdpRequest() {
    }

    public UdpRequest(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

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
