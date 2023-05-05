package edu.aau.g404.api.hue;

import edu.aau.g404.device.LightController;
import edu.aau.g404.device.SmartLight;
import edu.aau.g404.protocol.https.HttpsGetRequest;
import edu.aau.g404.protocol.https.HttpsPutRequest;
import edu.aau.g404.protocol.https.SSLHelper;

import java.util.List;

public final class HueController implements LightController {
    private String applicationKey;
    private String baseUrl;
    private HttpsPutRequest put = new HttpsPutRequest();

    public HueController(String bridgeIp, String applicationKey) {
        this.applicationKey = applicationKey;
        baseUrl = "https://" + bridgeIp;

        try {
            SSLHelper.disableSSLVerification();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void printLights() {
        HttpsGetRequest get = new HttpsGetRequest(baseUrl + "/clip/v2/resource/light", applicationKey);

        List<HueLight> lights = get.request().getData();

        for (HueLight light : lights) {
            System.out.printf("\u001B[32m%s\u001b[36m (%s)\n", light.getIdentifier(), light.getMetaData().getName());
        }
    }

    @Override
    public void updateLightState(String identifier, SmartLight light) {
        put.setUrl(baseUrl + "/clip/v2/resource/light/" + identifier).setApplicationKey(applicationKey);
        put.request(light);
    }

    @Override
    public SmartLight getLightClass() {
        return new HueLight();
    }
}
