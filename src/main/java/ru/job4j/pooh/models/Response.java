package ru.job4j.pooh.models;

public class Response {

    private final String text;
    private final int status;

    public Response(String text, int status) {
        this.text = text;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public int status() {
        return status;
    }

    public String statusMessage() {
        String result;
        switch (status) {
            case 200:
                result = "OK";
                break;
            case 404:
                result = "Not found";
                break;
            case 405:
                result = "Method Not Allowed";
                break;
            default:
                result = "Unknown";
        }
        return result;
    }
}
