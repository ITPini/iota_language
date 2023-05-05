package edu.aau.g404.protocol.https;

public abstract class Request {
    protected String requestType;
    protected String url;
    protected String applicationKey;

    public Request(String url, String applicationKey) {
        this.url = url;
        this.applicationKey = applicationKey;
    }

    public Request() {

    }

    public String getUrl() {
        return url;
    }

    public Request setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public Request setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
        return this;
    }
}
