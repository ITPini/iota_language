package edu.aau.g404.protocol.https;

import edu.aau.g404.device.SmartDevice;

import java.util.Map;

/**
 * HttpsPostRequest class handles HTTP POST requests. This class is work in progress.
 * Once completed, it will send data to an endpoint using the POST method.
 */
public final class HttpsPostRequest<T extends SmartDevice> extends HttpsRequest {
    public HttpsPostRequest(String url, Map<String, String> headers) {
        super(url, headers);
        super.requestType = "POST";
    }

    public HttpsPostRequest() {
        super.requestType = "POST";
    }

}