package edu.aau.g404.protocol.https;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.aau.g404.device.SmartDevice;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public final class HttpsPutRequest<T extends SmartDevice> extends HttpsRequest {
    public HttpsPutRequest(String url, Map<String, String> headers) {
        super(url, headers.get("hue-application-key"));
        super.requestType = "PUT";
        super.headers = headers;
    }

    public HttpsPutRequest() {
        super.requestType = "PUT";
    }

    public void request(T newState) {
        int responseCode;
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

            // Set request method
            connection.setRequestMethod(requestType);

            // Set header key-value
            for (Map.Entry<String, String> header : headers.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
            connection.setRequestProperty("Content-Type", "application/json"); // Only accept JSON currently

            // Enable input and output streams
            connection.setDoOutput(true);

            // Serialize Light object to JSON
            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL); // ObjectMapper configuration
            String jsonLight = objectMapper.writeValueAsString(newState);

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
            System.out.println("JSON body: " + objectMapper.writeValueAsString(newState)); // For debugging
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

