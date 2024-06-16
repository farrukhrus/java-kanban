package com.yandex.model;

import com.yandex.taskmanager.Status;
import com.yandex.taskmanager.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryTaskManagerTest {
    InMemoryHistoryManager manager = new InMemoryHistoryManager();

    @Test
    public void newTasksRetainThePreviousState() {
        // Задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
        // ??? Task в history должен хранить состояние task на момент добавления?
        Task task1 = new Task("Тренировка", "Силовая тренировка");
        manager.add(task1);

        task1.setName("Спать");
        task1.setDescription("Крепко спать");
        task1.setStatus(Status.IN_PROGRESS);

        List<Task> history = manager.getHistory();

        assertFalse(history.isEmpty());
        assertEquals(task1.getStatus(), history.getFirst().getStatus());
        assertEquals(task1.getName(), history.getFirst().getName());
        assertEquals(task1.getDescription(), history.getFirst().getDescription());
    }
}