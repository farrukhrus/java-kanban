package com.yandex.api;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.taskmanager.TaskManager;
import com.yandex.model.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class TasksHttpHandler extends BaseHttpHandler implements HttpHandler {
    public TasksHttpHandler(TaskManager taskManager) {
        super(taskManager);
    }

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
        Optional<Integer> taskIdOpt = getIdFromUrl(exchange);
        if (taskIdOpt.isPresent()) {
            int taskId = taskIdOpt.get();
            Optional<Task> taskOpt = Optional.ofNullable(taskManager.getTaskById(taskId));
            if (taskOpt.isPresent()) {
                Task task = taskOpt.get();
                sendText(exchange, gson.toJson(task), 200);
            } else {
                sendText(exchange, "Задачи с идентификатором %s не найдено", 404);
            }
        } else {
            sendText(exchange, "Некорректный идентификатор задачи", 400);
        }
    }

    private void handleGetTasks(HttpExchange exchange) throws IOException {
        List<Task> tasks = taskManager.getAllTasks();
        String taskListJson = gson.toJson(tasks);
        sendText(exchange, taskListJson, 200);
    }

    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        Optional<Integer> taskIdOpt = getIdFromUrl(exchange);
        if (taskIdOpt.isPresent()) {
            int taskId = taskIdOpt.get();
            taskManager.deleteTaskById(taskId);
            sendText(exchange, "Задача успешно удалена", 200);
        } else {
            sendText(exchange, "Некорректный идентификатор задачи", 400);
        }
    }

    private void handlePostTask(HttpExchange exchange) {
        try {
            String response = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Task task = gson.fromJson(response, Task.class);
            System.out.println("asdf");
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
            System.out.println("Что-то пошло не так\n\t" + e.getMessage());
        }
    }

    protected RequestType getRequestType(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        if (!pathParts[1].equals("tasks")) return RequestType.UNKNOWN;

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