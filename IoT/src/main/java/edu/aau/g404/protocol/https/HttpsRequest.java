package edu.aau.g404.protocol.https;

public abstract class HttpsRequest {
    protected String requestType;
    protected String url;
    protected String applicationKey;

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
}
