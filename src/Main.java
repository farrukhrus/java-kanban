import com.yandex.model.Epic;
import com.yandex.model.SubTask;
import com.yandex.model.Task;
import com.yandex.taskmanager.FileBackedTaskManager;
import com.yandex.taskmanager.InMemoryTaskManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src", "data.csv");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        final FileBackedTaskManager tm = FileBackedTaskManager.loadFromFile(path.toFile());
        //final FileBackedTaskManager tm = new FileBackedTaskManager(path.toFile());

        // ========== Задачи ========== //
        // создание задачи
        Task task1 = new Task("task1", "444");
        tm.addTask(task1);
        // обновление задачи
        Task task2 = new Task("task1", "555");
        task2.setId(task1.getId());
        tm.updateTask(task2);

        Task task3 = tm.addTask(new Task("task3", "333"));
        // System.out.println(task3);

        // ========== Эпики ========== //
        // создание эпика
        Epic e1 = new Epic("epic1", "epic1_before");
        tm.addEpic(e1);
        // обновление эпика
        Epic e2 = new Epic("epic1", "epic1_after");
        e2.setId(e1.getId());
        tm.updateEpic(e2);


        // ========== Подзадачи ========== //
        SubTask t1 = new SubTask("123", "456");
        SubTask t2 = new SubTask("123", "654");
        // добавление подзадачи в эпик
        tm.addSubTask(t1); // не задан эпик
        t2.setEpic(e2.getId());
        tm.addSubTask(t2);
        // обновление подзадачи
        SubTask t3 = new SubTask("123", "564");
        t3.setId(t2.getId());
        t3.setEpic(t2.getEpic());
        tm.updateSubTask(t3);

        // получить список подзадач по эпику
        // System.out.println( tm.getAllSubTasksByEpic(0) );

        // получить эпик/задачу/подзадачу по ID
        tm.getTaskById(task1.getId());
        tm.getTaskById(task2.getId());
        tm.getEpicById(e2.getId());
        tm.getTaskById(100);
        tm.getEpicById(e2.getId());

        // получить историю просмотра
        List<Task> history = tm.getHistory();
        System.out.println("\nИстория\nКол-во просмотров I: " + history.size() + "\n");
        for (var item : history) {
            System.out.println(item);
        }

        tm.getEpicById(e1.getId());
        tm.getEpicById(e2.getId());
        tm.getTaskById(task1.getId());
        tm.getSubTaskById(t3.getId());
        tm.getSubTaskById(t3.getId());
        tm.getSubTaskById(t3.getId());
        tm.getSubTaskById(t3.getId());
        tm.getSubTaskById(t3.getId());
        tm.getSubTaskById(t3.getId());
        tm.getTaskById(task1.getId());

        // история просмотра не больше 10 записей
        history = tm.getHistory();
        System.out.println("\nИстория\nКол-во просмотров II: " + history.size() + "\n");
        //System.out.println(history);
        for (var item : history) {
            System.out.println(item);
        }

        // удалить все задачи эпики
        //tm.deleteAllEpics();

        // удалить по ID
        //tm.deleteTaskById(task2.getId());
        // tm.deleteSubTaskById(t4.getId());
        // tm.deleteTaskById(task1.getId());
    }
}
