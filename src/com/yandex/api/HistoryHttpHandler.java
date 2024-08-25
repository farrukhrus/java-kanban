package com.yandex.api;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.model.Task;
import com.yandex.taskmanager.TaskManager;
import java.io.IOException;
import java.util.List;

public class HistoryHttpHandler extends BaseHttpHandler implements HttpHandler {
    public HistoryHttpHandler(TaskManager taskManager) {
        super(taskManager);
    }

    private final Gson gson = getGson();
    private final TaskManager taskManager = getTaskManager();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        List<Task> tasks = taskManager.getHistory();
        sendText(exchange, gson.toJson(tasks), 200);
    }
}