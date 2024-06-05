package com.yandex.taskmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
        // Проверяем, существует ли уже таск с таким ID
        if (isUnique(task)) {
            int taskId = generateId();
            task.setId(taskId);
            this.tasks.put(taskId, task);
        } else {
            System.out.println(Util.ANSI_RED + "Таск "+ task.getName() +" уже создан" + Util.ANSI_RESET);
        }
    }

    // создать эпик
    public void addEpic(Epic epic) {
        // Проверяем, существует ли уже эпик с таким ID
        if (isUnique(epic)) {
            int epicId = generateId();
            epic.setId(epicId);
            this.epics.put(epicId, epic);
        } else {
            System.out.println(Util.ANSI_RED + "Эпик с именем "+ epic.getName() +
                    " уже создан" + Util.ANSI_RESET);
        }
    }

    // создачу подзадачу в эпике
    public void addSubTask(SubTask subTask, Epic epic) {
        // новая подзадача
        if (isUnique(subTask)) {
            int subTaskId = generateId();
            subTask.setId(subTaskId);
            subTask.setEpic(epic.getId());

            epic.AddSubTask(subTaskId);
            this.subTasks.put(subTaskId, subTask);

            updateEpicOnChange(epic.getId());
        } else {
            System.out.println(Util.ANSI_RED + "Найден дубликат подзадачи " + subTask.getName() +
                    " в эпике " + epic.getName() + Util.ANSI_RESET);
        }
    }

    // вывести на экран все таски
    public void printAll() {
        if (this.tasks.isEmpty() && this.epics.isEmpty())
        {
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
        return new ArrayList<Task> (this.tasks.values());
    }

    // получить список эпиков
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<Epic> (this.epics.values());
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
        return new ArrayList<SubTask> (this.subTasks.values());
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

    // удалить все задачи
    public void deleteAll(Type type) {
        switch (type) {
            case ALL:
                this.tasks.clear();
                this.epics.clear();
                this.subTasks.clear();
                break;
            case EPIC:
                this.epics.clear();
                this.subTasks.clear();
                break;
            case TASK:
                this.tasks.clear();
                break;
            case SUBTASK:
                this.subTasks.clear();
                for (int id : epics.keySet()) {
                    updateEpicOnChange(id);
                }
                break;
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
        }
        {
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

    // обновить задачу
    private int getTaskByName(String name) {
        for (int key : tasks.keySet()) {
            if (Objects.equals(tasks.get(key).getName(), name)) {
                return tasks.get(key).getId();
            }
        }
        return -1;
    }

    public void updateTask (Task task) {
        int oldTaskId = getTaskByName(task.getName());
        if (oldTaskId != -1) {
            task.setId(oldTaskId);
            tasks.put(oldTaskId, task);
        } else {
            System.out.println("Задачи с таким названием нет: " + task.getName());
        }
    }

    // обновить эпик
    private int getEpicByName(String name) {
        for (int key : epics.keySet()) {
            if (Objects.equals(epics.get(key).getName(), name)) {
                return epics.get(key).getId();
            }
        }
        return -1;
    }

    public void updateEpic (Epic epic) {
        int oldEpicId = getEpicByName(epic.getName());
        if (oldEpicId != -1) {
            epic.setId(oldEpicId);
            epics.put(oldEpicId, epic);
        } else {
            System.out.println("Эписка с таким названием нет: " + epic.getName());
        }
    }

    // обновить подзадачу
    private int getSubTaskByName(String name, int epicId) {
        for (int key : subTasks.keySet()) {
            SubTask subTask = subTasks.get(key);
            if (Objects.equals(subTask.getName(), name) && subTask.getEpic() == epicId) {
                return subTask.getId();
            }
        }
        return -1;
    }

    public void updateSubTask(SubTask subTask) {
        // int id, SubTask subTask, Status status
        if (subTask.getEpic() != 0) {
            int oldSubTaskId = getSubTaskByName(subTask.getName(), subTask.getEpic());
            if (oldSubTaskId != -1) {
                subTask.setId(oldSubTaskId);
                subTasks.put(oldSubTaskId, subTask);
                updateEpicOnChange(subTask.getEpic());
            } else {
                System.out.println("Подзадачи с таким названием нет: " + subTask.getName());
            }
        } else {
            System.out.println("Подзадача должна содержать ссылку на эпик: " + subTask.getName());
        }
    }

    // обновить статус эпика при обновлении подзадач
    private void updateEpicOnChange(int epicId){
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

    // проверка на уникальность ID
    private boolean isUnique(Object task) {
        String type = task.getClass().getName();
        switch (type) {
            case "com.yandex.taskmanager.Task" -> {
                for (int k : tasks.keySet()) {
                    if (tasks.get(k).equals(task)) {
                        return false;
                    }
                }
            }
            case "com.yandex.taskmanager.Epic" -> {
                for (int k : epics.keySet()) {
                    if (epics.get(k).equals(task)) {
                        return false;
                    }
                }
            }
            case "com.yandex.taskmanager.SubTask" -> {
                for (int k : subTasks.keySet()) {
                    SubTask sub = (SubTask) task;
                    if (subTasks.get(k).equals(sub)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // генератор ID
    private int generateId() {
        return counter++;
    }
}
