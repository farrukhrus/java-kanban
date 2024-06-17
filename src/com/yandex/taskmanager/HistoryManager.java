package com.yandex.taskmanager;

import java.util.List;
import com.yandex.model.Task;

public interface HistoryManager {
    // пометить задачу как просмотренную
    void add(Task task);

    // вернуть список просмотренных задач
    List<Task> getHistory();

}