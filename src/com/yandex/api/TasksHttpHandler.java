package com.yandex.api;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.model.Task;
import com.yandex.taskmanager.TaskManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

public class TasksHttpHandler extends BaseHttpHandler implements HttpHandler {
    public TasksHttpHandler(TaskManager taskManager) {
        super(taskManager);
    }

    private final Gson gson = getGson();
    private final TaskManager taskManager = getTaskManager();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        RequestType reqType = getRequestType(exchange.getRequestURI().getPath(), exchange.getRequestMethod());
        switch (reqType) {
            case GET_ALL:
                handleGetTasks(exchange);
                break;
            case GET_BY_ID:
                handleGetTaskById(exchange);
                break;
            case CREATE:
                handlePostTask(exchange);
                break;
            case DELETE:
                handleDeleteTask(exchange);
                break;
            default:
                sendText(exchange, "Не найдено", 404);
        }
    }

    private void handleGetTaskById(HttpExchange exchange) throws IOException {
        Integer taskId = getIdFromUrl(exchange);
        Optional<Task> taskOpt = Optional.ofNullable(taskManager.getTaskById(taskId));
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            sendText(exchange, gson.toJson(task), 200);
        } else {
            sendText(exchange, "Задачи с идентификатором %s не найдено", 404);
        }
    }

    private void handleGetTasks(HttpExchange exchange) throws IOException {
        List<Task> tasks = taskManager.getAllTasks();
        String taskListJson = gson.toJson(tasks);
        sendText(exchange, taskListJson, 200);
    }

    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        int taskId = getIdFromUrl(exchange);
        taskManager.deleteTaskById(taskId);
        sendText(exchange, "Задача успешно удалена", 200);
    }

    private void handlePostTask(HttpExchange exchange) {
        try {
            String request = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Task task = gson.fromJson(request, Task.class);
            if (task.getId() == 0) {
                int id = taskManager.addTask(task).getId();
                if (id < 0) {
                    sendText(exchange, "Задача пересекается с уже существующими во времени", 406);
                    return;
                }
            } else {
                taskManager.updateTask(task);
            }
            exchange.sendResponseHeaders(201, 0);
            exchange.close();
        } catch (IOException e) {
            System.out.println(MessageFormat.format("Что-то пошло не так\n\t{0}", e.getMessage()));
        }
    }
}