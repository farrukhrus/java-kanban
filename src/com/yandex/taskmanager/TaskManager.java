package com.yandex.taskmanager;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int counter = 1;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, SubTask> subTasks;

    public TaskManager() {
        epics = new HashMap<>();
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
    }

    // создать задачу
    public void addTask(Task task) {
        int taskId = generateId();
        task.setId(taskId);
        this.tasks.put(taskId, task);
    }

    // создать эпик
    public void addEpic(Epic epic) {
        int epicId = generateId();
        epic.setId(epicId);
        this.epics.put(epicId, epic);
    }

    // создачу подзадачу в эпике
    public void addSubTask(SubTask subTask) {
        int subTaskId = generateId();
        subTask.setId(subTaskId);
        if (subTask.getEpic() != 0 && this.epics.containsKey(subTask.getEpic())) {
            Epic epic = this.epics.get(subTask.getEpic());
            epic.addSubTask(subTaskId);
            this.subTasks.put(subTaskId, subTask);
            updateEpicOnChange(epic.getId());
        } else {
            System.out.println("Подзадача не создана. Подзадача должна содержать ссылку на существующий эпик");
        }


    }

    // вывести на экран все таски
    public void printAll() {
        if (this.tasks.isEmpty() && this.epics.isEmpty()) {
            System.out.println("Список задач пуст");
        } else {
            if (!epics.isEmpty()) {
                System.out.println("Список эписков с подзадачами");
                for (Epic epic : this.epics.values()) {
                    System.out.println(epic.getName());
                    for (int i : epic.getSubTasks()) {
                        System.out.println("\t" + this.subTasks.get(i).getName());
                    }
                }
            }
            if (!tasks.isEmpty()) {
                System.out.println("\nСписок задач");
                for (Task task : this.tasks.values()) {
                    System.out.println(task.getName());
                }
            }
        }
    }

    // получить список задач
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<Task>(this.tasks.values());
    }

    // получить список эпиков
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<Epic>(this.epics.values());
    }

    // получить список подзадач по эпику
    public ArrayList<SubTask> getAllSubTasksByEpic(int epicId) {
        ArrayList<SubTask> subs = new ArrayList<>();
        for (int k : subTasks.keySet()) {
            if (subTasks.get(k).getEpic() == epicId) {
                subs.add(subTasks.get(k));
            }
        }
        return subs;
    }

    // получить список подзадач
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<SubTask>(this.subTasks.values());
    }

    // получить задачу по ID
    public Task getTaskById(int id) {
        return this.tasks.get(id);
    }

    // получить эпик по ID
    public Epic getEpicById(int id) {
        return this.epics.get(id);
    }

    // получить подзадачу по ID
    public SubTask getSubTaskById(int id) {
        return this.subTasks.get(id);
    }

    // удалить всё
    public void deleteAll() {
        this.tasks.clear();
        this.epics.clear();
        this.subTasks.clear();
    }

    // удалить все задачи
    public void deleteAllTasks() {
        this.tasks.clear();
    }

    // удалить все эпики
    public void deleteAllEpics() {
        this.epics.clear();
        this.subTasks.clear();
    }

    // удалить все подзадачи
    public void deleteAllSubTasks() {
        this.subTasks.clear();
        for (int id : epics.keySet()) {
            updateEpicOnChange(id);
        }
    }

    // удалить задачу
    public void deleteTaskById(int id) {
        if (tasks.get(id) != null) {
            this.tasks.remove(id);
        } else {
            System.out.println("Задача с таким ID не найдена");
        }
    }

    // удалить эпик
    public void deleteEpicById(int id) {
        if (epics.get(id) != null) {
            this.epics.remove(id);
            for (int k : this.subTasks.keySet()) {
                if (this.subTasks.get(k).getEpic() == id) {
                    this.subTasks.remove(k);
                }
            }
        } else {
            System.out.println("Эпик с таким ID не найден");
        }
    }

    // удалить подазачу
    public void deleteSubTaskById(int id) {
        if (subTasks.get(id) != null) {
            SubTask sub = subTasks.get(id);
            this.subTasks.remove(id);
            updateEpicOnChange(sub.getEpic());
        } else {
            System.out.println("Подзадача с таким ID не найдена");
        }
    }

    // удалить подзадачи по эпик ID
    public void deleteSubTasksByEpicId(int epicId){
        if (this.epics.containsKey(epicId)) {
            Epic epic = this.epics.get(epicId);
            for (int id : epic.getSubTasks()) {
                deleteSubTaskById(id);
            }
        }
    }

    // обновить задачу
    public void updateTask(Task task) {
        if (this.tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Задача не обновлена. Не найдена задача с ID - " + task.getId());
        }
    }

    // обновить эпик
    public void updateEpic(Epic epic) {
        if (this.epics.containsKey(epic.getId())) {
            deleteSubTasksByEpicId(epic.getId());
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Эпик не обновлен. Не найден эпик с ID - " + epic.getId());
        }

    }

    // обновить подзадачу
    public void updateSubTask(SubTask subTask) {
        if (subTask.getEpic() != 0 && this.epics.containsKey(subTask.getEpic())) {
            if (this.subTasks.containsKey(subTask.getId())) {
                subTasks.put(subTask.getId(), subTask);
                updateEpicOnChange(subTask.getEpic());
            } else {
                System.out.println("Подзадача не обновлена. Не найдена подзадача с ID - " + subTask.getId());
            }
        } else {
            System.out.println("Подзадача не обновлена. Подзадача должна содержать ссылку на существующий эпик");
        }
    }

    // обновить статус эпика при обновлении подзадач
    private void updateEpicOnChange(int epicId) {
        int size = 0;
        int countNew = 0;
        int countDone = 0;
        for (int k : subTasks.keySet()) {
            SubTask sub = subTasks.get(k);
            if (sub.getEpic() == epicId) {
                size++;
                if (sub.getStatus() == Status.DONE) {
                    countDone++;
                } else if (sub.getStatus() == Status.NEW) {
                    countNew++;
                }
            }
        }
        Epic epic = epics.get(epicId);
        if (size > 0) {
            if (countNew == size) {
                epic.setStatus(Status.NEW);
            } else if (countDone == size) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
            epics.put(epicId, epic);
        }
    }

    // генератор ID
    private int generateId() {
        return counter++;
    }
}
