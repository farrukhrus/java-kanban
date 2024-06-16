package com.yandex.taskmanager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    @Test
    void epicsAreEqualToEachOtherIfTheirIdAreEqual() {
        // Экземпляры класса Epic равны друг другу, если равен их id
        Epic epic1 = new Epic("Сдать спринт", "Сдать спринт,чтобы пройти дальше по программе");
        Epic epic2 = new Epic("Сдать ", "Сдать ");
        epic2.setId(epic1.getId());

        assertEquals(epic1, epic2);
    }
}