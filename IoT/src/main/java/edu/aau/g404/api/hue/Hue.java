package edu.aau.g404.hue;

import edu.aau.g404.device.Light;
import edu.aau.g404.protocol.https.GET;

import java.util.List;

public class Hue {
    private GET get = new GET();
    private String ip;

    public Hue(String ip, String key) {
        this.get.setApplicationKey(key);
        this.ip = ip;
    }

    public void printLights() {
        get.setUrl("https://" + ip + "/clip/v2/resource/light");

        List<Light> lights = get.request().getData();

        for (Light light : lights) {
            System.out.println("\u001B[32m" + light.getIdentifier() + "\u001b[36m" + " (" + light.getMetaData().getName() + ")");
        }
    }

    public void printGroups() {
        get.setUrl("https://" + ip + "/clip/v2/resource/grouped_light");
    }


}
