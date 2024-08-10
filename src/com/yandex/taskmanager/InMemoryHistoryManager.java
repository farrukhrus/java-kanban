package com.yandex.taskmanager;

import com.yandex.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> history = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        if (task != null) {
            if (history.containsKey(task.getId())) {
                remove(task.getId());
            }
            Node node = new Node(task);
            if (tail == null) {
                head = node;
            } else {
                tail.next = node;
                node.prev = tail;
            }
            tail = node;
            history.put(task.getId(), node);
        }
    }

    @Override
    public void remove(int id) {
        if (history.containsKey(id)) {
            Node node = history.get(id);
            final Node prev = node.prev;
            final Node next = node.next;

            if (prev == null && next == null) {
                tail = null;
                head = null;
                return;
            }

            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
            }

            if (next == null) {
                tail = prev;
            } else {
                next.prev = prev;
            }

            history.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node node = head;
        while (node != null) {
            history.add(node.task);
            node = node.next;
        }
        return history;
    }
}