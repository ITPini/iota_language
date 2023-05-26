package edu.aau.g404.protocol.https;

import edu.aau.g404.api.Deserializer;
import edu.aau.g404.device.SmartDevice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * HttpsGetRequest class handles HTTP GET requests and deserializes the JSON resposne into a list of SmartDevice objects.
 * @param <T> The type of object to deserialize from JSON, which must extend SmartDevice.
 */
public final class HttpsGetRequest<T extends SmartDevice> extends HttpsRequest {
    private Deserializer<T> deserializer;
    public HttpsGetRequest(String url, Map<String, String> headers, Deserializer<T> deserializer) {
        super(url, headers);
        super.requestType = "GET";
        this.deserializer = deserializer;
    }

    public HttpsGetRequest() {
        super.requestType = "GET";
    }

    /**
     * Executes the HTTP Get request, reads the response and deserializes it into a list of SmartDevice objects.
     * @return  A list of objects of type T, deserialized from the JSON response.
     */
    public List<T> request() {
        String jsonResponse = null;
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

            jsonResponse = response.toString();

            // Deserialize response
            return deserializer.deserialize(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpsGetRequest setDeserializer(Deserializer<T> deserializer) {
        this.deserializer = deserializer;
        return this;
    }
}