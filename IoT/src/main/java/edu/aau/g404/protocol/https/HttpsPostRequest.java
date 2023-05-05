package edu.aau.g404.protocol.https;

public final class HttpsPostRequest extends HttpsRequest {
    public HttpsPostRequest(String url, String applicationKey) {
        super(url, applicationKey);
        super.requestType = "POST";
    }

    public HttpsPostRequest() {
        super.requestType = "POST";
    }


}
