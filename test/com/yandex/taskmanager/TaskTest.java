package com.yandex.taskmanager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void tasksAreEqualToEachOtherIfTheirIdAreEqual() {
        // Экземпляры класса Task равны друг другу, если равен их id
        Task task1 = new Task("Поспать утром", "Важное дело #1");
        Task task2 = new Task("Поспать днём", "Важное дело #1 v2");
        task2.setId(task1.getId());

        assertEquals(task1, task2);
    }
}