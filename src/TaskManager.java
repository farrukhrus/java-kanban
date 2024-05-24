import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int counter = 0;
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
    public void addEpic(Epic epic, ArrayList<SubTask> subTasks) {
        if (!subTasks.isEmpty()) {
            // Проверяем, существует ли уже эпик с таким ID
            if (isUnique(epic)) {
                int epicId = generateId();
                epic.setId(epicId);
                // Добавляем подзадачу
                for (SubTask subTask : subTasks){
                    subTask.setEpic(epicId);
                    addSubTask(subTask, epic);
                }
                if (!epic.getSubTasks().isEmpty()) {
                    epics.put(epicId, epic);
                    updateEpicOnChange(epicId);
                } else {
                    System.out.println(Util.ANSI_RED + "Эпик "+ epic.getName() +
                            " не создан. Подзадачи не прошли верификацию." + Util.ANSI_RESET);
                }
            } else {
                System.out.println(Util.ANSI_RED + "Эпик с именем "+ epic.getName() +
                        " уже создан" + Util.ANSI_RESET);
            }
        } else {
            System.out.println(Util.ANSI_RED + "Эпик должен содержать одну или более подзадач" +
                    Util.ANSI_RESET);
        }
    }

    // создачу подзадачу в эпике
    public void addSubTask(SubTask subTask, Epic epic) {
        // новая подзадача
        if (isUnique(subTask)) {
            int subTaskId = generateId();
            subTask.setId(subTaskId);
            epic.AddSubTask(subTaskId);
            this.subTasks.put(subTaskId, subTask);
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
                    this.subTasks.remove(id);
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
    public void updateTask (int id, Task task, Status status) {
        if (isUnique(task)) {
            Task oldTask = tasks.get(id);
            if (oldTask.getStatus() == status) {
                System.out.println("Статус у задачи не изменился: " + task.getName());
            } else {
                task.setStatus(status);
            }

            task.setId(id);
            tasks.put(id, task);
        } else {
            System.out.println(Util.ANSI_RED + "Задача не уникальна: " + task.getName() + Util.ANSI_RESET);
        }
    }

    // обновить эпик
    public void updateEpic (int id, Epic epic) {
        if (isUnique(epic)) {
            epic.setId(id);
            epic.setSubTasks(epics.get(id).getSubTasks());
            epics.put(id, epic);
        }
        else {
            System.out.println(Util.ANSI_RED + "Эпик не уникален: " + epic.getName() + Util.ANSI_RESET);
        }
    }

    // обновить подзадачу
    public void updateSubTask(int id, SubTask subTask, Status status) {
        if (isUnique(subTask)) {
            SubTask oldSub = subTasks.get(id);
            subTask.setId(id);
            subTask.setEpic(oldSub.getEpic());
            if (oldSub.getStatus() == status) {
                System.out.println("Статус у подзадачи не изменился: " + subTask.getName());
            } else {
                subTask.setStatus(status);
            }

            subTasks.put(id, subTask);
            updateEpicOnChange(subTask.getEpic());
        } else {
            System.out.println(Util.ANSI_RED + "Подзадача " + subTask.getName() + " не уникальна в рамках эпика "
                    + epics.get(subTask.getId()).getName() + Util.ANSI_RESET);
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
        if (size > 0) {

            Epic epic = epics.get(epicId);
            if (countNew == size) {
                epic.setStatus(Status.NEW);
            } else if (countDone == size) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
            epics.put(epicId, epic);
        } else {
            epics.remove(epicId);
        }
    }

    // проверка на уникальность ID
    private boolean isUnique(Object task) {
        String type = task.getClass().getName();
        switch (type) {
            case "Task" -> {
                for (int k : tasks.keySet()) {
                    if (tasks.get(k).equals(task)) {
                        return false;
                    }
                }
            }
            case "Epic" -> {
                for (int k : epics.keySet()) {
                    if (epics.get(k).equals(task)) {
                        return false;
                    }
                }
            }
            case "SubTask" -> {
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
