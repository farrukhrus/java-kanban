package com.yandex.model;

import java.util.List;
import com.yandex.taskmanager.Task;

public interface HistoryManager {
    // пометить задачу как просмотренную
    void add(Task task);

    // вернуть список просмотренных задач
    List<Task> getHistory();

}