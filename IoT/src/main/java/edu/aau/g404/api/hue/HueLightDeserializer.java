package edu.aau.g404.api.hue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.aau.g404.api.Deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class HueLightDeserializer implements Deserializer<HueLight> {

    @Override
    public List<HueLight> deserialize(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<HueLight> currentSmartLights = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode data = root.get("data");

            if (data.isArray()) {
                for (JsonNode node : data) {
                    HueLight smartLight = objectMapper.treeToValue(node, HueLight.class);
                    currentSmartLights.add(smartLight);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currentSmartLights;
    }
}