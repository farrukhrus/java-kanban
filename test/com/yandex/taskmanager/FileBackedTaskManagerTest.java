package com.yandex.taskmanager;

import com.yandex.model.Epic;
import com.yandex.model.Status;
import com.yandex.model.SubTask;
import com.yandex.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;

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
        hm = Managers.getDefaultFileBackedTaskManager();
        hm.setFilePath(path.toFile());
        task1 = new Task("task1", "task1_before",
                Status.NEW, Duration.ofSeconds(14),
                LocalDateTime.of(2024, 8, 19, 20, 9, 25));
        epic1 = new Epic("epic1", "epic1_before", Status.NEW, Duration.ofSeconds(5000),
                LocalDateTime.of(2024, 8, 19, 20, 15, 15));
        subTask1 = new SubTask("subtask1","subtask1_before", Status.NEW, epic1.getId(),
                LocalDateTime.of(2024, 8, 19, 20, 15, 45),
                Duration.ofSeconds(50));
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

        FileBackedTaskManager hm2 = FileBackedTaskManager.loadFromFile(path.toFile());
        assertEquals(1, hm2.tasks.size(), "Задача загружена при загрузке из файла");
        assertEquals(1, hm2.subTasks.size(), "Подзадача загружена при загрузке из файла");
        assertEquals(1, hm2.epics.size(), "Эпик загружен при загрузке из файла");
    }
}