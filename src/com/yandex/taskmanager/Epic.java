package com.yandex.taskmanager;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Integer> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<Integer> subTasks) {
        this.subTasks = subTasks;
    }

    protected void addSubTask(int subTask) {
        this.subTasks.add(subTask);
    }

    protected void clearSubTasks() {
        subTasks.clear();
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
}
