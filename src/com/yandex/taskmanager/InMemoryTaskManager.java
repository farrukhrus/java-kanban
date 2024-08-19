package com.yandex.taskmanager;

import com.yandex.model.Epic;
import com.yandex.model.Status;
import com.yandex.model.SubTask;
import com.yandex.model.Task;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private static int counter = 1;
    protected final HashMap<Integer, Epic> epics;
    protected final HashMap<Integer, Task> tasks;
    protected final HashMap<Integer, SubTask> subTasks;
    private final HistoryManager historyManager;
    protected final Comparator<Task> taskDTComparator = Comparator.comparing(Task::getStartTime);
    private final TreeSet<Task> sorted = new TreeSet<>(taskDTComparator);

    public InMemoryTaskManager() {
        epics = new HashMap<>();
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    // получить историю просмотров
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    // получить отсортированный список задач
    public List<Task> getSorted() {
        return new ArrayList<>(sorted);
    }

    // создать задачу
    @Override
    public Task addTask(Task task) {
        int taskId = generateId();
        task.setId(taskId);

        if (isStartTimeValid(task) && task.getStartTime() != null) {
            sorted.add(task);
        }
        tasks.put(taskId, task);
        return task;
    }

    // создать эпик
    @Override
    public Epic addEpic(Epic epic) {
        int epicId = generateId();
        epic.setId(epicId);
        epics.put(epicId, epic);
        return epic;
    }

    // создачу подзадачу в эпике
    @Override
    public SubTask addSubTask(SubTask subTask) {
        int subTaskId = generateId();
        subTask.setId(subTaskId);
        if (subTask.getEpic() != 0 && epics.containsKey(subTask.getEpic())) {
            Epic epic = epics.get(subTask.getEpic());
            epic.addSubTask(subTaskId);
            subTasks.put(subTaskId, subTask);
            updateEpicOnChange(epic.getId());
            return subTask;
        } else {
            System.out.println("Подзадача не создана. Подзадача должна содержать ссылку на существующий эпик");
        }
        return null;
    }

    // получить список задач
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<Task>(tasks.values());
    }

    // получить список эпиков
    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<Epic>(epics.values());
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
        return new ArrayList<SubTask>(subTasks.values());
    }

    // получить задачу по ID
    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    // получить эпик по ID
    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    // получить подзадачу по ID
    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    // удалить всё
    @Override
    public void deleteAll() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
        sorted.clear();
    }

    // удалить все задачи
    @Override
    public void deleteAllTasks() {
        tasks.forEach((k, v) -> {
            sorted.removeIf(v::equals);
        });
        tasks.clear();
    }

    // удалить все эпики
    @Override
    public void deleteAllEpics() {
        epics.clear();
        subTasks.forEach((k, v) -> {
            sorted.removeIf(v::equals);
        });
        subTasks.clear();
    }

    // удалить все подзадачи
    @Override
    public void deleteAllSubTasks() {
        subTasks.forEach((k, v) -> {
            sorted.removeIf(v::equals);
        });
        subTasks.clear();
        epics.values().forEach(Epic::clearSubTasks);
    }

    // удалить задачу
    @Override
    public void deleteTaskById(int id) {
        if (tasks.get(id) != null) {
            tasks.remove(id);
        } else {
            System.out.println("Задача с таким ID не найдена");
        }
    }

    // удалить эпик
    @Override
    public void deleteEpicById(int id) {
        if (epics.get(id) != null) {
            epics.remove(id);
            for (int k : subTasks.keySet()) {
                if (subTasks.get(k).getEpic() == id) {
                    subTasks.remove(k);
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
            subTasks.remove(id);
            updateEpicOnChange(sub.getEpic());
        } else {
            System.out.println("Подзадача с таким ID не найдена");
        }
    }

    // удалить подзадачи по эпик ID
    @Override
    public void deleteSubTasksByEpicId(int epicId) {
        if (epics.containsKey(epicId)) {
            Epic epic = epics.get(epicId);
            for (int id : epic.getSubTasks()) {
                deleteSubTaskById(id);
            }
            updateEpicOnChange(epicId);
        }
    }

    // обновить задачу
    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Задача не обновлена. Не найдена задача с ID - " + task.getId());
        }
    }

    // обновить эпик
    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            deleteSubTasksByEpicId(epic.getId());
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Эпик не обновлен. Не найден эпик с ID - " + epic.getId());
        }

    }

    // обновить подзадачу
    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTask.getEpic() != 0 && epics.containsKey(subTask.getEpic())) {
            if (subTasks.containsKey(subTask.getId())) {
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
            // status
            if (countNew == size) {
                epic.setStatus(Status.NEW);
            } else if (countDone == size) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
            // duration
            long sumDuration = epic.getSubTasks().stream()
                    .mapToLong(id -> subTasks.get(id).getDuration().getSeconds())
                    .sum();
            epic.setDuration(Duration.ofSeconds(sumDuration));

            // startTime / endTime
            List<SubTask> epicsSubTasks = subTasks.values().stream()
                    .filter(subtask -> epic.getSubTasks().contains(subtask.getId()))
                    .collect(Collectors.toList());

            if (!epicsSubTasks.isEmpty()) {
                epicsSubTasks.sort(taskDTComparator);
                epic.setStartTime(epicsSubTasks.getFirst().getStartTime());
                epic.setEndTime(Optional.ofNullable(epicsSubTasks.getLast().getEndTime()));

                epics.put(epicId, epic);
            }
        }
    }

    // валидация времени
    private boolean isStartTimeValid(Task task) {
        boolean result = sorted.stream().noneMatch(sortedTask ->
                sortedTask.getEndTime().isAfter(task.getStartTime()));
        return result;
    }

    // генератор ID
    int generateId() {
        return InMemoryTaskManager.counter++;
    }
}
