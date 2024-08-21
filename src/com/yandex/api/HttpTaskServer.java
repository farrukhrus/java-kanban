package com.yandex.api;

import com.sun.net.httpserver.HttpServer;
import com.yandex.taskmanager.*;
import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    public static void main(String[] args) throws IOException {
        TaskManager taskManager = Managers.getDefault();
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/tasks", new TasksHttpHandler(taskManager));

        /*server.createContext("/subtasks", new SubtasksHttpHandler(taskManager));
        server.createContext("/epics", new EpicsHttpHandler(taskManager));
        server.createContext("/history", new HistoryHttpHandler(taskManager));
        server.createContext("/prioritized", new PrioritiziedHttpHandler(taskManager));*/

        server.start();
    }
}