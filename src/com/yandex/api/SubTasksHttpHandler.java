package com.yandex.api;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.model.SubTask;
import com.yandex.taskmanager.TaskManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class SubTasksHttpHandler extends BaseHttpHandler implements HttpHandler {
    public SubTasksHttpHandler(TaskManager taskManager) {
        super(taskManager);
    }

    private final Gson gson = getGson();
    private final TaskManager taskManager = getTaskManager();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        RequestType reqType = getRequestType(exchange.getRequestURI().getPath(), exchange.getRequestMethod());
        switch (reqType) {
            case GET_ALL:
                handleGetSubtasks(exchange);
                break;
            case GET_BY_ID:
                handleGetSubtaskById(exchange);
                break;
            case CREATE:
                handlePostSubtask(exchange);
                break;
            case DELETE:
                handleDeleteSubtask(exchange);
                break;
            default:
                sendText(exchange, "Не найдено", 404);
        }
    }

    private void handleGetSubtaskById(HttpExchange exchange) throws IOException {
        Integer taskId = getIdFromUrl(exchange);
        Optional<SubTask> subtaskOpt = Optional.ofNullable(taskManager.getSubTaskById(taskId));
        if (subtaskOpt.isPresent()) {
            SubTask subTask = subtaskOpt.get();
            sendText(exchange, gson.toJson(subTask), 200);
        } else {
            sendText(exchange, String.format("Подзадачи с идентификатором %s не найдено", taskId), 404);
        }
    }

    private void handleGetSubtasks(HttpExchange exchange) {
        try {
            List<SubTask> subTasks = taskManager.getAllSubTasks();
            sendText(exchange, gson.toJson(subTasks), 200);
        } catch (Exception e) {
            sendText(exchange, "Не удалось выдать список подзадач", 404);
        }
    }

    private void handleDeleteSubtask(HttpExchange exchange) throws IOException {
        int taskId = getIdFromUrl(exchange);
        taskManager.deleteSubTaskById(taskId);
        sendText(exchange, "Подзадача успешно удалена", 200);
    }

    private void handlePostSubtask(HttpExchange exchange) {
        try {
            String request = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            SubTask subtask = gson.fromJson(request, SubTask.class);
            if (subtask.getId() == 0) {
                int id = taskManager.addSubTask(subtask).getId();
                if (id < 0) {
                    sendText(exchange, "Подзадача пересекается с уже существующими во времени", 406);
                    return;
                }
            } else {
                taskManager.updateSubTask(subtask);
            }
            exchange.sendResponseHeaders(201, 0);
            exchange.close();
        } catch (IOException e) {
            System.out.printf("Что-то пошло не так\n\t%s%n", e.getMessage());
        }
    }
}