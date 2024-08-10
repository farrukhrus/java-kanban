package com.yandex.taskmanager;

import java.io.File;
import java.nio.file.Path;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getDefaultFileBackedTaskManager(Path file) {
        return new FileBackedTaskManager(file.toFile());
    }

    public static FileBackedTaskManager getDefaultFileBackedTaskManager(Path file, boolean loadFile) {
        FileBackedTaskManager fbt = new FileBackedTaskManager(file.toFile());
        if (loadFile) {
            return FileBackedTaskManager.loadFromFile(file.toFile());
        } else {
            return fbt;
        }
    }
}