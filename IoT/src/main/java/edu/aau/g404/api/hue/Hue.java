package edu.aau.g404.api.hue;

import edu.aau.g404.device.LightController;
import edu.aau.g404.device.SmartLight;
import edu.aau.g404.protocol.https.GET;
import edu.aau.g404.protocol.https.PUT;
import edu.aau.g404.protocol.https.SSLHelper;

import java.util.List;

public final class Hue implements LightController {
    private String applicationKey;
    private String baseUrl;
    private GET get;
    private PUT put;

    public Hue(String bridgeIp, String applicationKey) {
        this.applicationKey = applicationKey;
        baseUrl = "https://" + bridgeIp + "/clip/v2/resource/light/";
    }

    public void printLights() {
        get = new GET(baseUrl, applicationKey);

        List<HueLight> lights = get.request().getData();

        for (HueLight light : lights) {
            System.out.println("\u001B[32m" + light.getIdentifier() + "\u001b[36m" + " (" + light.getMetaData().getName() + ")");
        }
    }

    @Override
    public void updateLightState(String identifier, SmartLight light) {
        try {
            SSLHelper.disableSSLVerification();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        put = new PUT();
        put.setUrl(baseUrl + identifier).setApplicationKey(applicationKey);
        put.request((HueLight) light);
    }
}
