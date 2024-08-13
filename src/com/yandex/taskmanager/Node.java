package com.yandex.taskmanager;
import com.yandex.model.Task;

class Node {
    protected Task task;
    protected Node prev;
    protected Node next;

    protected Node(Task task) {
        this.task = task;
    }
}