package com.yandex.model;

import java.time.Duration;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Optional;

public class Epic extends Task {
    private ArrayList<Integer> subTasks = new ArrayList<>();
    private final TaskType taskType;
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
        this.taskType = TaskType.EPIC;
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
        this.taskType = TaskType.EPIC;
    }

    public Epic(int id, String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(id, name, description, status, duration, startTime);
        this.taskType = TaskType.EPIC;
    }

    public Epic(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        this.taskType = TaskType.EPIC;
    }

    public Epic(String name, String description, Status status, Duration duration,
                LocalDateTime startTime, ArrayList<Integer> subTasks) {
        super(name, description, status, duration, startTime);
        this.taskType = TaskType.EPIC;
        this.subTasks = subTasks;
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
        return taskType;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    @Override
    public String toString() {
        return "Epic {" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", type=" + getType() +
                ", duration=" + getDuration() +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                ", subTasks='" + getSubTasks() + '\'' + '}';
    }

    public String toCSV() {
        return (getId() + "," + getType() + "," + getName() + ","
                + getDescription() + "," + getStatus() + "," + getDuration() + "," + getStartTime() + ",");
    }
}
