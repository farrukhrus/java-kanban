package com.yandex.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.yandex.api.adapters.DurationAdapter;
import com.yandex.api.adapters.LocalDateTimeAdapter;
import com.yandex.taskmanager.TaskManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public class BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public BaseHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();
    }

    protected Integer getIdFromUrl(HttpExchange exchange) {
        String[] path = exchange.getRequestURI().getPath().split("/");
        try {
            Optional<Integer> taskIdOpt = Optional.of(Integer.parseInt(path[2]));
            return taskIdOpt.get();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    protected void sendText(HttpExchange h, String text, int respCode) {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        try {
            h.sendResponseHeaders(respCode, resp.length);
            h.getResponseBody().write(resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        h.close();
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public Gson getGson() {
        return gson;
    }

    protected RequestType getRequestType(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");
        Set<String> validTypes = Set.of("subtasks", "tasks", "epics");

        if (!validTypes.contains(pathParts[1])) {
            return RequestType.UNKNOWN;
        }

        if (pathParts.length == 3) {
            if (requestMethod.equals("GET")) {
                return RequestType.GET_BY_ID;
            }
            if (requestMethod.equals("DELETE")) {
                return RequestType.DELETE;
            }
        }
        if (pathParts.length == 2) {
            if (requestMethod.equals("GET")) {
                return RequestType.GET_ALL;
            }
            if (requestMethod.equals("POST")) {
                return RequestType.CREATE;
            }
        }
        return RequestType.UNKNOWN;
    }
}