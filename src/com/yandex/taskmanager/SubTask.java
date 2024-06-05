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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubTask subTask = (SubTask) o;
        return super.equals(o) && (epic == subTask.epic || subTask.epic == 0);
    }
}
