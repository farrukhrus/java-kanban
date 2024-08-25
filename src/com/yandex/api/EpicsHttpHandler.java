package com.yandex.api;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.model.Epic;
import com.yandex.taskmanager.TaskManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class EpicsHttpHandler extends BaseHttpHandler implements HttpHandler {
    public EpicsHttpHandler(TaskManager taskManager) {
        super(taskManager);
    }

    private final Gson gson = getGson();
    private final TaskManager taskManager = getTaskManager();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        RequestType reqType = getRequestType(exchange.getRequestURI().getPath(), exchange.getRequestMethod());
        switch (reqType) {
            case GET_ALL:
                handleGetEpics(exchange);
                break;
            case GET_BY_ID:
                handleGetEpicById(exchange);
                break;
            case CREATE:
                handlePostEpic(exchange);
                break;
            case DELETE:
                handleDeleteEpic(exchange);
                break;
            default:
                sendText(exchange, "Не найдено", 404);
        }
    }

    private void handleGetEpics(HttpExchange exchange) {
        List<Epic> epics = taskManager.getAllEpics();
        sendText(exchange, gson.toJson(epics), 200);
    }

    private void handleGetEpicById(HttpExchange exchange) throws IOException {
        Integer taskId = getIdFromUrl(exchange);
        Optional<Epic> epicOpt = Optional.ofNullable(taskManager.getEpicById(taskId));
        if (epicOpt.isPresent()) {
            Epic epic = epicOpt.get();
            sendText(exchange, gson.toJson(epic), 200);
        } else {
            sendText(exchange, String.format("Эпик с идентификатором %s не найдено", taskId), 404);
        }
    }

    private void handlePostEpic(HttpExchange exchange) {
        try {
            String request = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

            if (request.isEmpty()) {
                sendText(exchange, "Тело запроса не должно быть пустым", 400);
                return;
            }

            Epic epic = gson.fromJson(request, Epic.class);
            if (epic.getId() == 0) {
                taskManager.addEpic(epic);
            } else {
                taskManager.updateEpic(epic);
            }
            exchange.sendResponseHeaders(201, 0);
            exchange.close();
        } catch (IOException e) {
            System.out.printf("Что-то пошло не так\n\t%s%n", e.getMessage());
        }
    }

    private void handleDeleteEpic(HttpExchange exchange) throws IOException {
        int taskId = getIdFromUrl(exchange);
        taskManager.deleteEpicById(taskId);
        sendText(exchange, "Эпик успешно удален", 200);
    }
}