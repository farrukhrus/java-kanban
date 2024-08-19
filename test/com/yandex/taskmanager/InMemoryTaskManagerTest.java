
package com.yandex.taskmanager;

import com.yandex.model.Epic;
import com.yandex.model.Status;
import com.yandex.model.Task;
import com.yandex.model.SubTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryHistoryManagerTest {
    private final InMemoryTaskManager manager = new InMemoryTaskManager();

    private Task task1;

    @BeforeEach
    public void dataInit() {
        task1 = new Task("task1", "task1_before",
                Status.NEW, Duration.ofSeconds(14),
                LocalDateTime.of(2024, 8, 19, 20, 9, 25));

        Task task2 = new Task("task2", "task1_before",
                Status.NEW, Duration.ofSeconds(14),
                LocalDateTime.of(2024, 8, 20, 20, 9, 25));

        Epic epic1 = new Epic("epic1", "epic1_before", Status.NEW, Duration.ofSeconds(5000),
                LocalDateTime.of(2024, 8, 19, 20, 15, 15));
        SubTask subTask1 = new SubTask("subtask1","subtask1_before", Status.NEW, epic1.getId(),
                LocalDateTime.of(2024, 8, 19, 20, 15, 45),
                Duration.ofSeconds(50));

        manager.addEpic(epic1);
        manager.addTask(task1);
        manager.addTask(task2);

        subTask1.setEpic(epic1.getId());
        manager.addSubTask(subTask1);
        epic1.addSubTask(subTask1.getId());

    }

    @Test
    @DisplayName("Проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер")
    public void taskIsUnchangedAfterAddingToManager() {
        Task savedTask = manager.getTaskById(task1.getId());

        assertNotNull(savedTask);
        assertEquals(task1.getName(), savedTask.getName());
        assertEquals(task1.getDescription(), savedTask.getDescription());
        assertEquals(task1.getStatus(), savedTask.getStatus());

    }

    @Test
    @DisplayName("Задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера")
    public void givenAndGeneratedIdsDoNotConflict() {
        Task task3 = new Task("task3", "task3_before",
                Status.NEW, Duration.ofSeconds(14),
                LocalDateTime.of(2024, 8, 21, 20, 9, 25));
        task3.setId(8);
        manager.addTask(task3);
        assertEquals(3, manager.getAllTasks().size());
    }
}