package com.yandex.taskmanager;

import com.yandex.model.*;
import com.yandex.util.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTaskManager() {
        super();
    }

    public void setFilePath(File file) {
        this.file = file;
    }

    // сохранить изменения в файл
    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            bw.write("id,type,name,desc,status,duration,startTime,epic\n");

            for (Task task : getAllTasks()) {
                bw.write(task.toCsv() + "\n");
            }
            for (Epic epic : getAllEpics()) {
                bw.write(epic.toCsv() + "\n");
            }
            for (SubTask subTask : getAllSubTasks()) {
                bw.write(subTask.toCsv() + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Возникла ошибка при записи файла.");
        }
    }

    // загрузить данные из файла
    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fm = new FileBackedTaskManager();
        fm.setFilePath(file);
        Task task = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                if (!line.isEmpty() || !line.isBlank()) {
                    task = fromString(line);
                }
                switch (task.getType()) {
                    case TASK:
                        fm.tasks.put(task.getId(), task);
                        break;
                    case EPIC:
                        fm.epics.put(task.getId(), (Epic) task);
                        break;
                    case SUBTASK:
                        fm.subTasks.put(task.getId(), (SubTask) task);
                        break;
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Возникла ошибка при чтении файла.");
        }
        return fm;
    }

    public static Task fromString(String value) {
        String[] row = value.split(",");

        int id = Integer.parseInt(row[0]);
        TaskType type = TaskType.valueOf(row[1]);
        String name = row[2];
        String desc = row[3];
        Status status = Status.valueOf(row[4]);
        Duration duration = row[5].equalsIgnoreCase("null") ? null : Duration.parse(row[5]);
        LocalDateTime startTime = row[6].equalsIgnoreCase("null") ? null : LocalDateTime.parse(row[6]);

        switch (type) {
            case TASK:
                return new Task(id, name, desc, status, duration, startTime);
            case EPIC:
                return new Epic(id, name, desc, status, duration, startTime);
            case SUBTASK:
                int epicId = Integer.parseInt(row[7]);
                return new SubTask(id, name, desc, status, epicId, duration, startTime);
            default:
                throw new ManagerSaveException("Неизвестный тип задачи");
        }
    }

    @Override
    public Task addTask(Task task) {
        Task taskToReturn = super.addTask(task);
        save();
        return taskToReturn;
    }

    @Override
    public Epic addEpic(Epic epic) {
        Epic epicToReturn = super.addEpic(epic);
        save();
        return epicToReturn;
    }

    @Override
    public SubTask addSubTask(SubTask subtask) {
        SubTask subTaskToReturn = super.addSubTask(subtask);
        save();
        return subTaskToReturn;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic newEpic) {
        super.updateEpic(newEpic);
        save();
    }

    @Override
    public void updateSubTask(SubTask newSubtask) {
        super.updateSubTask(newSubtask);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        return super.getTaskById(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return super.getEpicById(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        return super.getSubTaskById(id);
    }
}