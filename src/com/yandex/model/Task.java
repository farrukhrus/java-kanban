package com.yandex.model;

import java.util.Objects;
import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    private String name;
    private String description;
    private Status status;
    private TaskType taskType;
    private int id;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Task(String name, String description) {
        this.status = Status.NEW;
        this.name = name;
        this.description = description;
        this.taskType = TaskType.TASK;
    }

    public Task(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        this.taskType = TaskType.TASK;
        this.endTime = startTime.plus(duration);
    }

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.description = description;
        this.taskType = TaskType.TASK;
    }

    public Task(int id, String name, String description, Status status,
                Duration duration, LocalDateTime startTime) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
        this.taskType = TaskType.TASK;
        this.endTime = startTime.plus(duration);
    }

    public void setTaskType(TaskType type) {
        this.taskType = type;
    }

    public TaskType getTaskType() {
        return this.taskType;
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public String toCsv() {
        return (getId() + "," + getType() + "," + getName() + "," +
                getDescription() + "," + getStatus() + "," +
                getDuration() + "," + getStartTime() + ",");
    }

    @Override
    public String toString() {
        return "Task {" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", type=" + getType() +
                ", duration=" + getDuration() +
                ", startTime=" + getStartTime() +
                ", startTime=" + getEndTime() +
                '}';
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

