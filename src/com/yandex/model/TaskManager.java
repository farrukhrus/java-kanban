package com.yandex.model;

import com.yandex.taskmanager.Epic;
import com.yandex.taskmanager.Status;
import com.yandex.taskmanager.SubTask;
import com.yandex.taskmanager.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    // создать задачу
    void addTask(Task task);

    // создать эпик
    void addEpic(Epic epic);

    // создачу подзадачу в эпике
    void addSubTask(SubTask subTask);

    // вывести на экран все таски
    void printAll();

    // получить список задач
    ArrayList<Task> getAllTasks();

    // получить список эпиков
    ArrayList<Epic> getAllEpics();

    // получить список подзадач по эпику
    ArrayList<SubTask> getAllSubTasksByEpic(int epicId);

    // получить список подзадач
    ArrayList<SubTask> getAllSubTasks();

    // получить задачу по ID
    Task getTaskById(int id);

    // получить эпик по ID
    Epic getEpicById(int id);

    // получить подзадачу по ID
    SubTask getSubTaskById(int id);

    // удалить всё
    void deleteAll();

    // удалить все задачи
    void deleteAllTasks();

    // удалить все эпики
    void deleteAllEpics();

    // удалить все подзадачи
    void deleteAllSubTasks();

    // удалить задачу
    void deleteTaskById(int id);

    // удалить эпик
    void deleteEpicById(int id);

    // удалить подазачу
    void deleteSubTaskById(int id);

    // удалить подзадачи по эпик ID
    void deleteSubTasksByEpicId(int epicId);

    // обновить задачу
    void updateTask(Task task);

    // обновить эпик
    void updateEpic(Epic epic);

    // обновить подзадачу
    void updateSubTask(SubTask subTask);

    List<Task> getHistory();
}
