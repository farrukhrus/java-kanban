package com.yandex.api;

import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.yandex.api.adapters.DurationAdapter;
import com.yandex.api.adapters.LocalDateTimeAdapter;
import com.yandex.taskmanager.TaskManager;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class BaseHttpHandler {
    TaskManager taskManager;
    Gson gson;

    public BaseHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();
    }

    protected Optional<Integer> getIdFromUrl(HttpExchange exchange) {
        String[] path = exchange.getRequestURI().getPath().split("/");

        try {
            return Optional.of(Integer.parseInt(path[2]));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    protected void sendText(HttpExchange h, String text, int respCode) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(respCode, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }
}