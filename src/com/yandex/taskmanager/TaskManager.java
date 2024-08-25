package com.yandex.taskmanager;

import com.yandex.model.Epic;
import com.yandex.model.SubTask;
import com.yandex.model.Task;

import java.util.List;

public interface TaskManager {
    // создать задачу
    Task addTask(Task task);

    // создать эпик
    Epic addEpic(Epic epic);

    // создачу подзадачу в эпике
    SubTask addSubTask(SubTask subTask);

    // получить список задач
    List<Task> getAllTasks();

    // получить список эпиков
    List<Epic> getAllEpics();

    // получить список подзадач
    List<SubTask> getAllSubTasks();

    // получить список подзадач по эпику
    List<SubTask> getAllSubTasksByEpic(int epicId);

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

    List<Task> getPrioritizedTasks();
}
