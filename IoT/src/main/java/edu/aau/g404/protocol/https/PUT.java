package edu.aau.g404.protocol.https;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.aau.g404.device.Light;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PUT {
    private String url;
    private String applicationKey;

    public PUT(String url, String applicationKey) {
        this.url = url;
        this.applicationKey = applicationKey;
    }

    public PUT() {

    }

    public String request(Light light) {
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

            // Set request method
            connection.setRequestMethod("PUT");

            // Set header key-value
            connection.setRequestProperty("hue-application-key", applicationKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable input and output streams
            connection.setDoOutput(true);

            // Serialize Light object to JSON
            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL); // ObjectMapper configuration
            String jsonLight = objectMapper.writeValueAsString(light);

            // Write JSON body
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(jsonLight);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            BufferedReader responseReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String responseLine;
            StringBuilder responseBuilder = new StringBuilder();

            while ((responseLine = responseReader.readLine()) != null) {
                responseBuilder.append(responseLine);
            }
            responseReader.close();

            System.out.println("Response Code : " + connection.getResponseCode()); // For debugging
            System.out.println("JSON body: " + objectMapper.writeValueAsString(light)); // For debugging

            // Print response
            return responseBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUrl() {
        return url;
    }

    public PUT setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public PUT setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
        return this;
    }
}

