package edu.aau.g404.protocol.https;

import java.util.Map;

public abstract class HttpsRequest {
    protected String requestType;
    protected String url;
    protected String applicationKey;
    protected Map<String, String> headers;

    public HttpsRequest(String url, String applicationKey) {
        this.url = url;
        this.applicationKey = applicationKey;
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

    public String getApplicationKey() {
        return applicationKey;
    }

    public HttpsRequest setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
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
