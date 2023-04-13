package edu.aau.g404.protocol.https;

public abstract class Request {
    protected String url;
    protected String applicationKey;

    public Request(String url, String applicationKey) {
        this.url = url;
        this.applicationKey = applicationKey;
    }

    public Request() {

    }
}
