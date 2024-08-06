package com.yandex.model;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subTasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public ArrayList<Integer> getSubTasks() {
        return subTasks;
    }

    public void addSubTask(int subTask) {
        this.subTasks.add(subTask);
    }

    public void clearSubTasks() {
        subTasks.clear();
    }

    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public String toString() {
        return "Epic {" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", subTasks='" + getSubTasks() + '\'' + '}';
    }

    public String toCSV() {
        return (getId() + "," + getType() + "," + getName() + ","
                + getDescription() + "," + getStatus() + ",");
    }
}
