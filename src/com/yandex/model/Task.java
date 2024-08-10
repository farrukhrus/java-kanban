package com.yandex.model;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private Status status;
    private final TaskType taskType;
    private int id;

    public Task(String name, String description) {
        this.status = Status.NEW;
        this.name = name;
        this.description = description;
        this.taskType = TaskType.TASK;
    }

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.description = description;
        this.taskType = TaskType.TASK;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public TaskType getType() {
        return taskType;
    }

    @Override
    public String toString() {
        return "Task {" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' + '}';
    }

    public String toCSV() {
        return (getId() + "," + getType() + "," + getName() + ","
                + getDescription() + "," + getStatus() + ",");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

