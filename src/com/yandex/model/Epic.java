package com.yandex.model;

import java.time.Duration;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Epic extends Task {
    private final ArrayList<Integer> subTasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
        setTaskType(TaskType.EPIC);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
        setTaskType(TaskType.EPIC);
    }

    public Epic(int id, String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(id, name, description, status, duration, startTime);
        setTaskType(TaskType.EPIC);
    }

    public Epic(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        setTaskType(TaskType.EPIC);
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
        return getTaskType();
    }

    public void setEndTime(LocalDateTime endTime) {
        super.setEndTime(endTime);
    }

    public LocalDateTime getEndTime() {
        return super.getEndTime();
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

    public String toCsv() {
        return (getId() + "," + getType() + "," + getName() + "," +
                getDescription() + "," + getStatus() + "," + getDuration() + "," + getStartTime() + ",");
    }
}
