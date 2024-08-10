package com.yandex.taskmanager;

import com.yandex.model.Epic;
import com.yandex.model.SubTask;
import com.yandex.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    static FileBackedTaskManager hm;
    static Task task1;
    static Epic epic1;
    static SubTask subTask1;
    static Path path;

    @BeforeEach
    void beforeach() throws IOException {
        path = Paths.get("resources", "data.csv");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        hm = Managers.getDefaultFileBackedTaskManager(path);
        task1 = new Task("Тренировка", "Силовая тренировка");
        epic1 = new Epic("Тренировка", "Силовая тренировка");
        subTask1 = new SubTask("Тренировка", "Силовая тренировка");
    }

    @Test
    void shouldNotHaveTasksWhenWithoutLoad() {
        assertEquals(0, hm.subTasks.size(), "Отсутствуют подзадачи");
        assertEquals(0, hm.epics.size(), "Отсутствуют подзадачи");
        assertEquals(0, hm.tasks.size(), "Отсутствуют задачи");
    }

    @Test
    void shouldHaveTasksWhenLoadFromFile() {
        hm.addTask(task1);
        hm.addEpic(epic1);
        subTask1.setEpic(epic1.getId());
        hm.addSubTask(subTask1);

        FileBackedTaskManager hm2 = Managers.getDefaultFileBackedTaskManager(path, true);
        assertEquals(1, hm2.tasks.size(), "Задача загружена при загрузке из файла");
        assertEquals(1, hm2.subTasks.size(), "Подзадача загружена при загрузке из файла");
        assertEquals(1, hm2.epics.size(), "Эпик загружен при загрузке из файла");
    }
}