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
    private PUT put = new PUT();

    public Hue(String bridgeIp, String applicationKey) {
        this.applicationKey = applicationKey;
        baseUrl = "https://" + bridgeIp + "/clip/v2/resource/light";

        try {
            SSLHelper.disableSSLVerification();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void printLights() {
        GET get = new GET(baseUrl, applicationKey);

        List<HueLight> lights = get.request().getData();

        for (HueLight light : lights) {
            System.out.printf("\u001B[32m%s\u001b[36m (%s)\n", light.getIdentifier(), light.getMetaData().getName());
        }
    }

    @Override
    public void updateLightState(String identifier, SmartLight light) {
        put.setUrl(baseUrl + "/" + identifier).setApplicationKey(applicationKey);
        put.request(light);
    }

    @Override
    public SmartLight getLightClass() {
        return new HueLight();
    }
}
