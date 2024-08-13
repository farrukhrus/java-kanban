package com.yandex.taskmanager;

import com.yandex.model.Status;
import com.yandex.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private final InMemoryHistoryManager manager = new InMemoryHistoryManager();

    @Test
    @DisplayName("Задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.")
    public void newTasksRetainThePreviousState() {
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

    @Test
    @DisplayName("Задачи успешно добавляются в историю просмотров")
    void testAddNewTask() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("task1", "555");
        historyManager.add(task);

        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История просмотров обновилась");
    }

    @Test
    @DisplayName("Задачи успешно удаляются из истории просмотров")
    public void testAddAndRemoveHistory() {
        HistoryManager hm = new InMemoryHistoryManager();
        Task task1 = new Task("task1", "111");
        task1.setId(1);
        Task task2 = new Task("task2", "222");
        task2.setId(2);

        hm.add(task1);
        hm.add(task2);

        List<Task> history = hm.getHistory();
        assertEquals(2, history.size());

        hm.remove(task1.getId());
        history = hm.getHistory();
        assertEquals(1, history.size());
    }

    @Test
    @DisplayName("В истории просмотров отсутствуют дубликаты")
    public void testAddDuplicateTask() {
        HistoryManager hm = new InMemoryHistoryManager();
        Task task1 = new Task("task1", "111");
        task1.setId(1);
        hm.add(task1);
        hm.add(task1);
        List<Task> history = hm.getHistory();
        assertEquals(1, history.size());
    }
}