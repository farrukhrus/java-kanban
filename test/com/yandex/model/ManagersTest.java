package com.yandex.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ManagersTest {
    @Test
    void utilityClassReturnsReadyToUseInstances() {
        // Утилитарный класс всегда возвращает готовый к работе экземпляр TaskManager
        Assertions.assertNotNull(Managers.getDefault());
    }

    @Test
    void methodGetDefaultHistoryIsNotNull() {
        // Утилитарный класс всегда возвращает готовый к работе экземпляр HistoryManager
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }
}