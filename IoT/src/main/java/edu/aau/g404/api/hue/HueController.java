package edu.aau.g404.api.hue;

import edu.aau.g404.device.light.LightController;
import edu.aau.g404.device.light.SmartLight;
import edu.aau.g404.protocol.https.HttpsGetRequest;
import edu.aau.g404.protocol.https.HttpsPutRequest;
import edu.aau.g404.protocol.https.SSLHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides methods for controlling and interacting with Philips Hue smart lights.
 */
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

    public HueController(){

    }

    /**
     * Retrieves the list of Hue light connected to the Hue Bridge and prints their identifiers and names.
     */
    public void printLights() {
        Map<String, String> headers = new HashMap<>();
        headers.put("hue-application-key", applicationKey);

        HttpsGetRequest get = new HttpsGetRequest(baseUrl + "/clip/v2/resource/light", headers, new HueLightDeserializer());

        List<HueLight> lights = get.request();

        for (HueLight light : lights) {
            System.out.printf("\u001B[32m%s\u001b[36m (%s)\n", light.getIdentifier(), light.getMetaData().getName());
        }
    }

    /**
     * Updates the light state of a Hue light.
     * @param identifier    The identifer of the light to update.
     * @param newLightState The new light state to be applied to the Hue light.
     */
    @Override
    public void updateLightState(String identifier, SmartLight newLightState) {
        Map<String, String> headers = new HashMap<>();
        headers.put("hue-application-key", applicationKey);

        put.setUrl(baseUrl + "/clip/v2/resource/light/" + identifier).setHeaders(headers);
        put.request(newLightState);
    }

    @Override
    public SmartLight getLightClass() {
        return new HueLight();
    }
}