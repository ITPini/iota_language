package edu.aau.g404.protocol.https;

import edu.aau.g404.api.Deserializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public final class HttpsGetRequest<T> extends HttpsRequest {
    private Deserializer<T> deserializer;
    public HttpsGetRequest(String url, Map<String, String> headers, Deserializer<T> deserializer) {
        super(url, headers.get("hue-application-key"));
        super.requestType = "GET";
        super.headers = headers;
        this.deserializer = deserializer;
    }

    public HttpsGetRequest() {
        super.requestType = "GET";
    }

    public List<T> request() {
        String jsonResponse = null;
        int responseCode = 0;

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Deserialize response
        return deserializer.deserialize(jsonResponse);
    }

    public HttpsGetRequest setDeserializer(Deserializer<T> deserializer) {
        this.deserializer = deserializer;
        return this;
    }
}
