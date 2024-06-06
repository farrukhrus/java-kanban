package com.yandex.taskmanager;

public class SubTask extends Task {
    private int epic;

    public SubTask(String name, String description) {
        super(name, description);
    }

    public int getEpic() {
        return epic;
    }

    public void setEpic(int epic) {
        this.epic = epic;
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
}
