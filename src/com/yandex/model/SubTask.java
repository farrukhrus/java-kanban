package com.yandex.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    private int epic;
    private final TaskType taskType;
    private LocalDateTime endTime;

    public SubTask(String name, String description) {
        super(name, description);
        this.taskType = TaskType.SUBTASK;
    }

    public SubTask(int id, String name, String description, Status status, int epic) {
        super(id, name, description, status);
        this.epic = epic;
        this.taskType = TaskType.SUBTASK;
    }

    public SubTask(int id, String name, String description, Status status,
                   int epic, Duration duration, LocalDateTime startTime) {
        super(id, name, description, status, duration, startTime);
        this.epic = epic;
        this.taskType = TaskType.SUBTASK;
        this.endTime = startTime.plus(duration);
    }

    public SubTask(String name, String description, Status status, int epicId,
                   LocalDateTime startTime, Duration duration) {
        super(name, description, status, duration, startTime);
        this.epic = epicId;
        this.taskType = TaskType.SUBTASK;
        this.endTime = startTime.plus(duration);
    }

    public int getEpic() {
        return epic;
    }

    public void setEpic(int epic) {
        this.epic = epic;
    }

    public TaskType getType() {
        return taskType;
    }

    @Override
    public String toString() {
        return "SubTask {" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", type=" + getType() +
                ", duration=" + getDuration() +
                ", startTime=" + getStartTime() +
                ", startTime=" + getEndTime() +
                ", epicID='" + getEpic() + '\'' + '}';
    }

    public String toCsv() {
        return (getId() + "," + getType() + "," + getName() + "," +
                getDescription() + "," + getStatus() + "," +
                getDuration() + "," + getStartTime() + "," + getEpic());
    }
}
