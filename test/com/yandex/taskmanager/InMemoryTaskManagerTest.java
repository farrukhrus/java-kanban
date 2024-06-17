
package com.yandex.taskmanager;

import com.yandex.model.Epic;
import com.yandex.model.Task;
import com.yandex.model.SubTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryHistoryManagerTest {
    private final InMemoryTaskManager manager = new InMemoryTaskManager();

    Task task1;

    @BeforeEach
    public void dataInit() {
        task1 = new Task("Спать", "Крепко спать");
        Task task2 = new Task("Есть", "Сытно есть");
        Epic epic1 = new Epic("Работать", "Усердно работать");
        SubTask subTask1 = new SubTask("Кодить", "Безудержно кодить");

        manager.addEpic(epic1);
        manager.addTask(task1);
        manager.addTask(task2);

        subTask1.setEpic(epic1.getId());
        epic1.addSubTask(subTask1.getId());
        manager.addSubTask(subTask1);
    }

    @Test
    public void taskIsUnchangedAfterAddingToManager() {
        // Проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
        Task savedTask = manager.getTaskById(task1.getId());

        assertNotNull(savedTask);
        assertEquals(task1.getName(), savedTask.getName());
        assertEquals(task1.getDescription(), savedTask.getDescription());
        assertEquals(task1.getStatus(), savedTask.getStatus());

    }

    @Test
    public void givenAndGeneratedIdsDoNotConflict() {
        //задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера
        Task task3 = new Task("Учиться", "Добросовестно учиться");
        task3.setId(8);
        manager.addTask(task3);
        assertEquals(3, manager.getAllTasks().size());
    }
}