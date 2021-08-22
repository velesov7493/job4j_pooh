package ru.job4j.pooh.services;

import ru.job4j.pooh.models.Request;
import ru.job4j.pooh.models.Response;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class QueueService implements Service {

    private Map<String, Queue<String>> messages;

    public QueueService() {
        messages = new ConcurrentHashMap<>();
    }

    private String getMessage(String queueName) {
        Queue<String> messagesQueue = messages.get(queueName);
        return messagesQueue != null ? messagesQueue.poll() : "";
    }

    private void postMessage(String queueName, String text) {
        Queue<String> nq = new ConcurrentLinkedDeque<>();
        Queue<String> mq = messages.putIfAbsent(queueName, nq);
        Queue<String> queue = mq == null ? nq : mq;
        queue.offer(text);
    }

    public Response process(Request request) {
        Response result;
        if ("GET".equals(request.method())) {
            String msg = getMessage(request.resourceName());
            int status = msg == null || msg.isEmpty() ? 404 : 200;
            if (status == 200) {
                result = new Response(msg, status);
                System.out.println("Сообщение передано. Статус [" + status + "].");
            } else {
                result = new Response("", status);
                System.out.println("Провал передачи сообщения. Статус [" + status + "].");
            }
        } else if ("POST".equals(request.method())) {
            postMessage(request.resourceName(), request.text());
            result = new Response("Сообщение добавлено в очередь.", 200);
            System.out.println("Сообщение [" + request.text() + "] добавлено в очередь.");
        } else {
            result = new Response("", 405);
            System.out.println("Метод http [" + request.method() + "] не поддерживается.");
        }
        return result;
    }
}
