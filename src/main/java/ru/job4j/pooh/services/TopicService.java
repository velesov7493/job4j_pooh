package ru.job4j.pooh.services;

import ru.job4j.pooh.models.Request;
import ru.job4j.pooh.models.Response;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TopicService implements Service {

    private static final int TOTAL_CLIENTS = 50;

    private Map<String, Queue<String>> messages;

    public TopicService() {
        messages = new ConcurrentHashMap<>();
    }

    private String getMessage(String topicName, int clientId) {
        String tName = String.format("%s[%d]", topicName, clientId);
        Queue<String> messagesQueue = messages.get(tName);
        return messagesQueue != null ? messagesQueue.poll() : "";
    }

    private void postMessage(String topicName, String text) {
        for (int i = 1; i <= TOTAL_CLIENTS; i++) {
            String tName = String.format("%s[%d]", topicName, i);
            Queue<String> nq = new ConcurrentLinkedDeque<>();
            Queue<String> mq = messages.putIfAbsent(tName, nq);
            Queue<String> queue = mq == null ? nq : mq;
            queue.offer(text);
        }
    }

    public Response process(Request request) {
        Response result;
        if ("GET".equals(request.method())) {
            String msg = getMessage(request.resourceName(), request.clientId());
            int status = msg == null || msg.isEmpty() ? 404 : 200;
            if (status == 200) {
                result = new Response(msg, status);
                System.out.println("Сообщение темы передано. Статус [" + status + "].");
            } else {
                result = new Response("", status);
                System.out.println("Провал передачи сообщения темы. Статус [" + status + "].");
            }
        } else if ("POST".equals(request.method())) {
            postMessage(request.resourceName(), request.text());
            result = new Response("Сообщение добавлено в тему.\r\n", 200);
            System.out.println("Сообщение [" + request.text() + "] добавлено в тему.");
        } else {
            result = new Response("", 405);
            System.out.println("Метод http [" + request.method() + "] не поддерживается.");
        }
        return result;
    }
}
