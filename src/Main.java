import com.yandex.model.Epic;
import com.yandex.model.Status;
import com.yandex.model.SubTask;
import com.yandex.model.Task;
import com.yandex.taskmanager.FileBackedTaskManager;
import com.yandex.taskmanager.Managers;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("resources", "data.csv");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        //final FileBackedTaskManager tm = Managers.getDefaultFileBackedTaskManager(path);
        //FileBackedTaskManager.loadFromFile(path.toFile());
        final FileBackedTaskManager tm = FileBackedTaskManager.loadFromFile(path.toFile());
        //final FileBackedTaskManager tm = new FileBackedTaskManager(path.toFile());

        // ========== Задачи ========== //
        // создание задачи
        Task task1 = new Task("task1", "task1_before", Status.NEW, Duration.ofSeconds(14),
                LocalDateTime.of(2024, 8, 19, 20, 9, 25));
        tm.addTask(task1);

        Task task3 = new Task("task3", "task3 comment", Status.NEW, Duration.ofSeconds(14),
                LocalDateTime.of(2024, 8, 19, 21, 9, 25));
        tm.addTask(task3);

        Task task4 = new Task("task4", "task4 comment", Status.NEW, Duration.ofSeconds(14),
                LocalDateTime.of(2024, 8, 19, 22, 9, 25));
        tm.addTask(task4);

        // вывести список задач
        /*for (var item : tm.getAllTasks()){
            System.out.println(item);
        }
        System.out.println("######");
        for (var item : tm.getSorted()){
            System.out.println(item);
        }*/

        // ========== Эпики ========== //
        // создание эпика
        Epic e1 = new Epic("epic1", "epic1_before", Status.NEW, Duration.ofSeconds(5000),
                LocalDateTime.of(2024, 8, 19, 20, 15, 15));
        tm.addEpic(e1);
        // обновление эпика
        Epic e2 = new Epic("epic1", "epic1_after", Status.NEW, Duration.ofSeconds(5000),
                LocalDateTime.of(2024, 8, 19, 20, 15, 45));
        e2.setId(e1.getId());
        tm.updateEpic(e2);

        // ========== Подзадачи ========== //
        SubTask t1 = new SubTask("subtask1", "subtask1_before", Status.NEW, e1.getId(),
                LocalDateTime.of(2024, 8, 19, 20, 15, 45),
                Duration.ofSeconds(50)
        );
        SubTask t3 = new SubTask("subtask3", "subtask3_before", Status.NEW, e1.getId(),
                LocalDateTime.of(2024, 8, 19, 20, 19, 45),
                Duration.ofSeconds(50)
        );

        // добавление подзадачи в эпик
        tm.addSubTask(t1);
        tm.addSubTask(t3);

        // история просмотра не больше 10 записей
        tm.getSubTaskById(5);
        tm.getEpicById(4);
        List<Task> history = tm.getHistory();
        System.out.println("\nИстория\nКол-во просмотров II: " + history.size() + "\n");
        for (var item : history) {
            System.out.println(item);
        }

        //
        /*System.out.println("All subs before deleteAllEpics: " + tm.getAllSubTasks());
        System.out.println("All epics before deleteAllEpics: " + tm.getAllEpics());
        tm.deleteAllEpics();
        System.out.println("All subs after deleteAllEpics: " + tm.getAllSubTasks());
        System.out.println("All epics after deleteAllEpics: " + tm.getAllEpics());*/
    }
}
