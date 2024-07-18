package com.yandex.taskmanager;

import com.yandex.model.Epic;
import com.yandex.model.Status;
import com.yandex.model.SubTask;
import com.yandex.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private static int counter = 1;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, SubTask> subTasks;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        epics = new HashMap<>();
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        this.historyManager = new InMemoryHistoryManager();
    }

    // получить историю просмотров
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    // создать задачу
    @Override
    public Task addTask(Task task) {
        int taskId = generateId();
        task.setId(taskId);
        this.tasks.put(taskId, task);
        return task;
    }

    // создать эпик
    @Override
    public Epic addEpic(Epic epic) {
        int epicId = generateId();
        epic.setId(epicId);
        this.epics.put(epicId, epic);
        return epic;
    }

    // создачу подзадачу в эпике
    @Override
    public SubTask addSubTask(SubTask subTask) {
        int subTaskId = generateId();
        subTask.setId(subTaskId);
        if (subTask.getEpic() != 0 && this.epics.containsKey(subTask.getEpic())) {
            Epic epic = this.epics.get(subTask.getEpic());
            epic.addSubTask(subTaskId);
            this.subTasks.put(subTaskId, subTask);
            updateEpicOnChange(epic.getId());
            return subTask;
        } else {
            System.out.println("Подзадача не создана. Подзадача должна содержать ссылку на существующий эпик");
        }
        return null;
    }

    // вывести на экран все таски
    @Override
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
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<Task>(this.tasks.values());
    }

    // получить список эпиков
    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<Epic>(this.epics.values());
    }

    // получить список подзадач по эпику
    @Override
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
    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<SubTask>(this.subTasks.values());
    }

    // получить задачу по ID
    @Override
    public Task getTaskById(int id) {
        Task task = this.tasks.get(id);
        historyManager.add(task);
        return task;
    }

    // получить эпик по ID
    @Override
    public Epic getEpicById(int id) {
        Epic epic = this.epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    // получить подзадачу по ID
    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = this.subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    // удалить всё
    @Override
    public void deleteAll() {
        this.tasks.clear();
        this.epics.clear();
        this.subTasks.clear();
    }

    // удалить все задачи
    @Override
    public void deleteAllTasks() {
        this.tasks.clear();
    }

    // удалить все эпики
    @Override
    public void deleteAllEpics() {
        this.epics.clear();
        this.subTasks.clear();
    }

    // удалить все подзадачи
    @Override
    public void deleteAllSubTasks() {
        this.subTasks.clear();
        for (int id : epics.keySet()) {
            updateEpicOnChange(id);
        }
    }

    // удалить задачу
    @Override
    public void deleteTaskById(int id) {
        if (tasks.get(id) != null) {
            this.tasks.remove(id);
        } else {
            System.out.println("Задача с таким ID не найдена");
        }
    }

    // удалить эпик
    @Override
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
    @Override
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
    @Override
    public void deleteSubTasksByEpicId(int epicId) {
        if (this.epics.containsKey(epicId)) {
            Epic epic = this.epics.get(epicId);
            for (int id : epic.getSubTasks()) {
                deleteSubTaskById(id);
            }
            updateEpicOnChange(epicId);
        }
    }

    // обновить задачу
    @Override
    public void updateTask(Task task) {
        if (this.tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Задача не обновлена. Не найдена задача с ID - " + task.getId());
        }
    }

    // обновить эпик
    @Override
    public void updateEpic(Epic epic) {
        if (this.epics.containsKey(epic.getId())) {
            deleteSubTasksByEpicId(epic.getId());
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Эпик не обновлен. Не найден эпик с ID - " + epic.getId());
        }

    }

    // обновить подзадачу
    @Override
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
    void updateEpicOnChange(int epicId) {
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
    int generateId() {
        return InMemoryTaskManager.counter++;
    }
}
