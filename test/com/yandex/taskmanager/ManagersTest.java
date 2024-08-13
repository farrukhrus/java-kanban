package com.yandex.taskmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class ManagersTest {
    @Test
    @DisplayName("Утилитарный класс всегда возвращает готовый к работе экземпляр TaskManager")
    public void utilityClassReturnsReadyToUseInstances() {
        Assertions.assertNotNull(Managers.getDefault());
    }

    @Test
    @DisplayName("Утилитарный класс всегда возвращает готовый к работе экземпляр HistoryManager")
    public void methodGetDefaultHistoryIsNotNull() {
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }

    @Test
    @DisplayName("Утилитарный класс всегда возвращает готовый к работе экземпляр HistoryManager")
    public void methodGetDefaultFileBackedTaskManagerIsNotNull() throws IOException {
        Path path = Paths.get("resources", "data.csv");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        Assertions.assertNotNull(Managers.getDefaultFileBackedTaskManager(path));
    }
}