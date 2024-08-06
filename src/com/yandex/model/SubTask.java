package com.yandex.model;

public class SubTask extends Task {
    private int epic;

    public SubTask(String name, String description) {
        super(name, description);
    }

    public SubTask(int id, String name, String description, Status status, int epic) {
        super(id, name, description, status);
        this.epic = epic;
    }

    public int getEpic() {
        return epic;
    }

    public void setEpic(int epic) {
        this.epic = epic;
    }

    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return "SubTask {" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", epicID='" + getEpic() + '\'' + '}';
    }

    public String toCSV() {
        return (getId() + "," + getType() + "," + getName() + ","
                + getDescription() + "," + getStatus() + "," + getEpic());
    }
}
