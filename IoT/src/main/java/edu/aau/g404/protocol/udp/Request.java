package edu.aau.g404.protocol.udp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.aau.g404.api.wiz.WiZLight;

import java.io.IOException;
import java.net.*;

// TODO: Better naming convention for this class
public class Request {
    protected String ip;
    protected int port;

    public Request() {
    }

    public Request(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void request(WiZLight light) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String payload = objectMapper.writeValueAsString(light);

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

    public Request setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Request setPort(int port) {
        this.port = port;
        return this;
    }
}
