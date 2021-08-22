package ru.job4j.pooh.models;

import java.util.Objects;

public class Request {

    private final String method;
    private final String uri;
    private final String text;

    private Request(String aMethod, String aUri, String aText) {
        method = aMethod;
        uri = aUri;
        text = aText;
    }

    public static Request of(String content) {
        String[] lines = content.split("\\r\\n");
        String[] words = lines[0].split(" ");
        StringBuilder text = new StringBuilder();
        int i = 2;
        while (i < lines.length && !lines[i].isEmpty()) {
            i++;
        }
        while (i < lines.length) {
            text.append(lines[i++]);
        }
        return new Request(words[0], words[1], text.toString());
    }

    public String method() {
        return method;
    }

    public String mode() {
        String[] crumbs = uri.split("/", 3);
        Objects.checkIndex(1, crumbs.length);
        return crumbs[1];
    }

    public String resourceName() {
        String[] crumbs = uri.split("/");
        Objects.checkIndex(2, crumbs.length);
        return crumbs[2];
    }

    public int clientId() {
        String[] crumbs = uri.split("/");
        Objects.checkIndex(3, crumbs.length);
        int result = -1;
        try {
            result = Integer.parseInt(crumbs[3]);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public String text() {
        return text;
    }
}
