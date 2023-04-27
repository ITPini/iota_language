package edu.aau.g404.protocol.https;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.aau.g404.api.hue.HueLight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class GET extends Request {
    public GET(String url, String applicationKey) {
        super(url, applicationKey);
        super.requestType = "GET";
    }

    public GET() {
        super.requestType = "GET";
    }

    public Response<List<HueLight>> request() {
        String jsonResponse = null;
        int responseCode = 0;

        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

            // Set request method
            connection.setRequestMethod(requestType);

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

            responseCode = connection.getResponseCode();
            System.out.println("Response Code : " + responseCode); // For debugging

            // Deserialize response
            jsonResponse = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Response<>(responseCode, deserializeLights(jsonResponse));
    }

    public List<HueLight> deserializeLights(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<HueLight> smartLights = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode data = root.get("data");

            if (data.isArray()) {
                for (JsonNode node : data) {
                    HueLight smartLight = objectMapper.treeToValue(node, HueLight.class);
                    smartLights.add(smartLight);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return smartLights;
    }
}
