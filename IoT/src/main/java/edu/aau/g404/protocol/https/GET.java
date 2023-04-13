package edu.aau.g404.protocol.https;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.aau.g404.device.Light;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GET {
    private String url;
    private String applicationKey;

    public GET(String url, String applicationKey){
        this.url = url;
        this.applicationKey = applicationKey;
    }

    public GET() {

    }

    public List<Light> deserializeLights(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Light> lights = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode data = root.get("data");

            if (data.isArray()) {
                for (JsonNode node : data) {
                    Light light = objectMapper.treeToValue(node, Light.class);
                    lights.add(light);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lights;
    }

    public List<Light> request() {
        String jsonResponse = null;

        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Set header key-value
            connection.setRequestProperty("hue-application-key", applicationKey);

            BufferedReader responseReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String responseLine;
            StringBuffer response = new StringBuffer();

            while ((responseLine = responseReader.readLine()) != null) {
                response.append(responseLine);
            }
            responseReader.close();

            System.out.println("Response Code : " + connection.getResponseCode()); // For debugging

            // Deserialize response
            jsonResponse = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deserializeLights(jsonResponse);
    }

    public String getUrl() {
        return url;
    }

    public GET setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public GET setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
        return this;
    }
}
