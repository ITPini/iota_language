package edu.aau.g404.protocol.https;

public class POST extends Request {
    public POST(String url, String applicationKey) {
        super(url, applicationKey);
        super.requestType = "POST";
    }

    public POST() {
        super.requestType = "POST";
    }


}
