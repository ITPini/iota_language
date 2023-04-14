package edu.aau.g404.protocol.https;

public class Response<T> {
    private int responseCode;
    private T data; // TODO: Consider the use of T (type variable)

    public Response(int responseCode, T data) {
        this.responseCode = responseCode;
        this.data = data;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public T getData() {
        return data;
    }
}
