package com.yandex.taskmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}