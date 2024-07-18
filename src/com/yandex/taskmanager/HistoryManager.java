package com.yandex.taskmanager;

import java.util.List;
import com.yandex.model.Task;

public interface HistoryManager {
    // пометить задачу как просмотренную
    void add(Task task);

    // удалить задачу из списка просмотренных
    void remove(int id);

    // вернуть список просмотренных задач
    List<Task> getHistory();

}