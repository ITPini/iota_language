package edu.aau.g404.protocol.https;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.aau.g404.api.hue.HueLight;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PUT extends Request {
    public PUT(String url, String applicationKey) {
        super(url, applicationKey);
        super.requestType = "PUT";
    }

    public PUT() {
        super.requestType = "PUT";
    }

    public Response request(HueLight light) {
        int responseCode;
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

            // Set request method
            connection.setRequestMethod(requestType);

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

            responseCode = connection.getResponseCode();

            System.out.println("Response Code : " + responseCode); // For debugging
            System.out.println("JSON body: " + objectMapper.writeValueAsString(light)); // For debugging

            // Print response
            return new Response(responseCode, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response(400, null);
    }
}

