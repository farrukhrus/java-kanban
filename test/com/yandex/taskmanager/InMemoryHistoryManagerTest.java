package com.yandex.taskmanager;

import com.yandex.model.Status;
import com.yandex.model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryTaskManagerTest {
    private final InMemoryHistoryManager manager = new InMemoryHistoryManager();

    @Test
    public void newTasksRetainThePreviousState() {
        // Задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
        Task task1 = new Task("Тренировка", "Силовая тренировка");
        manager.add(task1);

        task1.setName("Спать");
        task1.setDescription("Крепко спать");
        task1.setStatus(Status.IN_PROGRESS);

        List<Task> history = manager.getHistory();

        assertFalse(history.isEmpty());
        assertEquals(task1.getStatus(), history.get(0).getStatus());
        assertEquals(task1.getName(), history.get(0).getName());
        assertEquals(task1.getDescription(), history.get(0).getDescription());
    }
}