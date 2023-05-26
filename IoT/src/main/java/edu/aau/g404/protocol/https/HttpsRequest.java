package edu.aau.g404.protocol.https;

import java.util.Map;

/**
 * Abstract class HttpsRequest represents a base class for HTTPS requests. It contains common fields and methods
 * that can be extended by subclasses for specific request types (e.g., GET, PUT, POST).
 */
public abstract class HttpsRequest {
    protected String requestType;
    protected String url;
    protected Map<String, String> headers;

    public HttpsRequest(String url, Map<String, String> headers) {
        this.url = url;
        this.headers = headers;
    }

    public HttpsRequest() {

    }

    public String getUrl() {
        return url;
    }

    public HttpsRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpsRequest setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
}